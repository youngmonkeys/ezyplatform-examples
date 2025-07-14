package org.youngmonkeys.personal.socket.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.youngmonkeys.personal.socket.app.entity.Wheel;
import org.youngmonkeys.personal.socket.app.entity.WheelFragment;
import org.youngmonkeys.personal.socket.app.entity.WheelPrizeType;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@EzySingleton
public class WheelService {
    private Wheel wheel;
    private List<WheelFragment> emptyFragments;
    private List<Integer> randomRanges;
    private final Object lock = new Object();
    
    // because if we random in wheel.fragments.size (it's too small) so
    // we will get a lot of duplicate random, so we need choose a large size to ensure the random
    private static final int RANDOM_MAX_VALUE = 1000000;
    
    @EzyPostInit
    public void postInit() {
        wheel = loadWheelFromJsonFile();
        
        emptyFragments = wheel.getFragments()
                .stream()
                .filter(it -> it.getPrizeType() == WheelPrizeType.EMPTY)
                .collect(Collectors.toList());
        AtomicInteger lastRange = new AtomicInteger(0);
        randomRanges = wheel.getFragments()
                .stream()
                .map(it -> lastRange.addAndGet((int) ((it.getRatio() / 100.0) * RANDOM_MAX_VALUE)))
                .collect(Collectors.toList());
    }
    
    private Wheel loadWheelFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = new EzyAnywayInputStreamLoader().load("wheel.json");
        try {
            Wheel res = objectMapper.readValue(inputStream, Wheel.class);
            res.setId("wheel");
            return res;
        } catch (IOException e) {
            throw new IllegalStateException("can not load wheel", e);
        }
    }
    
    public int spin() {
        int randomIndex = calculateFragmentIndexFromRandomRange();
        synchronized (lock) {
            WheelFragment fragment = wheel.getFragments().get(randomIndex);
            if (fragment.getPrizeType() == WheelPrizeType.EMPTY) {
                return randomIndex;
            }
            if (fragment.getQuantity() > 0) {
                int remain = fragment.getQuantity() - 1;
                fragment.setQuantity(remain);
                return randomIndex;
            }
        }
        return randomEmptyIndex();
    }
    
    private int calculateFragmentIndexFromRandomRange() {
        int randomValue = ThreadLocalRandom.current().nextInt(RANDOM_MAX_VALUE);
        int randomIndex = -1;
        for (int i = 0; i < randomRanges.size(); ++i) {
            int range = randomRanges.get(i);
            if (randomValue <= range) {
                randomIndex = i;
                break;
            }
        }
        if (randomIndex == -1) {
            randomIndex = randomEmptyIndex();
        }
        return randomIndex;
    }
    
    private int randomEmptyIndex() {
        int emptyIndex = ThreadLocalRandom.current().nextInt(emptyFragments.size());
        return emptyFragments.get(emptyIndex).getIndex();
    }
    
    public void decreaseQuantity(int result) {
        synchronized (lock) {
            WheelFragment fragment = wheel.getFragments().get(result);
            int curQuantity = fragment.getQuantity();
            int newQuantity = (curQuantity > 0) ? curQuantity - 1 : 0;
            fragment.setQuantity(newQuantity);
        }
    }
}
