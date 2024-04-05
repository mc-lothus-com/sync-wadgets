package com.lothus.wadgets.sync.player.status;

import com.lothus.wadgets.sync.collectibles.status.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusInfo {

    private Status.Color color;
    private String status;

}
