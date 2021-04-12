package cz.czechitas.java2webapps.ukol2;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  public MainController(ResourceLoader resourceLoader) throws URISyntaxException, IOException {
    Resource citatyResource = resourceLoader.getResource("classpath:/citaty.txt");
    Resource obrazkyResource = resourceLoader.getResource("classpath:/obrazky.txt");

    messages = readAllLines(citatyResource);
    images = readAllLines(obrazkyResource);
  }

  @GetMapping("/")
  public ModelAndView home() {
    String imageURL = String.format("https://source.unsplash.com/%s/1600x900", images.get(random.nextInt(images.size())));

    ModelAndView result = new ModelAndView("index");
    result.addObject("message", messages.get(random.nextInt(messages.size())));
    result.addObject("image", imageURL);
    return result;
  }

  private static List<String> readAllLines(Resource resource) throws IOException {
    try (
            InputStream inputStream = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)
    ) {
      return reader
              .lines()
              .collect(Collectors.toList());
    }
  }
}
