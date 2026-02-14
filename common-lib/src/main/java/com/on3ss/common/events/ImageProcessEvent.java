package com.on3ss.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageProcessEvent {
    private UUID todoId;
    private String sftpFileName;
    private String originalName;
}
