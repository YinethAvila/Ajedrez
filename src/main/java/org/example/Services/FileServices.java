package org.example.Services;

import javax.swing.*;
import java.util.List;

public interface FileServices {
    List<String> readFile(String path);
    String getResourceURL(String folder, String fileName);
    ImageIcon getImageIcon(String fileName);
}
