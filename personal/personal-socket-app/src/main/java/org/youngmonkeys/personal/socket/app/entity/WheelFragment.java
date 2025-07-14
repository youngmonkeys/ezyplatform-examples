package org.youngmonkeys.personal.socket.app.entity;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Data;

@Data
@EzyObjectBinding
public class WheelFragment {
    private int index;
    private WheelPrizeType prizeType;
    private int prizeValue;
    private int quantity;
    private int ratio;
}
