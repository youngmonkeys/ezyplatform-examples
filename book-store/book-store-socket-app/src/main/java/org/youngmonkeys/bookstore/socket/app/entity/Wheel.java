package org.youngmonkeys.bookstore.socket.app.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import lombok.Data;

import java.util.List;

@Data
public class Wheel {
    @EzyId
    private String id;
    private List<WheelFragment> fragments;
}
