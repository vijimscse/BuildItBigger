package com.example;

import java.util.Random;

/**
 * Created by Viji
 * Class to provide the jokes
 */
public class JokeProvider {
    private static String[] jokes = new String[]{
            "I know HTML How to meet ladies.",
            "My attitude is not bad it's in Beta.",
            "When code is little code after some time.",
            "CAPS LOCK – Preventing Login Since 1980.",
            "I do not enjoy computer jokes, not one bit",
            "To err is human – and to blame it on a computer is even more so.",
            "The truth is out there. Anybody got the URL?",
            "The box said ‘Requires Windows Vista or better’. So I installed LINUX.",
            "I would love to change the world, but they won’t give me the source code.",
            "Hey! It compiles! Ship it!",
            "My attitude isn’t bad. It’s in beta",
            "Programmers are tools for converting caffeine into code."
    };

    /**
     *
     * @return Joke string
     */
    public static String getJoke() {
        int size = jokes.length;
        Random random = new Random();
        int index = random.nextInt(size);
        return jokes[index];
    }
}
