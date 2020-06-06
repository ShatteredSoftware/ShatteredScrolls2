package com.github.shatteredsuite.scrolls.config;

import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDeserializeBindingDisplay {

    private static Gson gson;
    private static String fileContents;

    @BeforeClass
    public static void setUp() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.setPrettyPrinting().create();
        try {
            ClassLoader loader = TestDeserializeBindingDisplay.class.getClassLoader();
            File file = new File(
                Objects.requireNonNull(loader.getResource("bindingDisplay.json")).getFile());
            StringBuilder builder = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }
            fileContents = builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBindingDisplayDeserialize() {
        BindingDisplay display = gson.fromJson(fileContents, BindingDisplay.class);
        Assert.assertEquals("For should be set to Unbound and serialize to type",
            "Unbound Teleportation Scroll", display.getName()
        );
    }
}
