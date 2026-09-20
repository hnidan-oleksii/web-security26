/*
 * Author: Oleksii Hnidan
 * Project: web-security26 | Lab: 1 | Class: Track
 */
package chnu.edu.websecurity26.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    private String id;
    private String title;
    private String artist;
    private String album;
    private Integer durationSeconds;
}
