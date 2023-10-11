package net.wintang.zooapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
