package org.example.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Metadata {
    private String event;
    private String site;
    private String eventDate;
    private Player white;
    private Player black;
    private String result;
}
