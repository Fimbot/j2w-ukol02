package cz.czechitas.java2webapps.ukol2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Filip Jirsák
 */
@Controller
public class MainController {
  private final Random random = new Random();
  private final List<String> messages;
  private final List<String> images;

  public MainController() throws URISyntaxException, IOException {
    messages = readAllLines("citaty.txt");

    images = Arrays.asList("95YRwf6CNw8", "m_HRfLhgABo", "xrVDYZRGdw4", "UWRqlJcDCXA", "CDbB4EJ4i_A", "uEcSKKDB1pg", "1td5Iq5IvNc", "70Rir5vB96U", "XJXWbfSo2f0",
            "fPkvU7RDmCo", "L2cxSuKWbpo", "Skf7HxARcoc", "f7xiGC81KTE");
  }

  @GetMapping("/")
  public ModelAndView home() {
    String imageURL = String.format("https://source.unsplash.com/%s/1600x900", images.get(random.nextInt(images.size())));
    String bodyStyle = String.format("background-image: url(%s);", imageURL);

    ModelAndView result = new ModelAndView("index");
    result.addObject("message", messages.get(random.nextInt(messages.size())));
    result.addObject("bodyStyle", bodyStyle);
    return result;
  }

  private static List<String> readAllLines(String resource) throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(resource);
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      return reader
              .lines()
              .collect(Collectors.toList());
    }
  }
}
