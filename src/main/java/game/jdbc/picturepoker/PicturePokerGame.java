package game.jdbc.picturepoker;

import com.fasterxml.jackson.core.JsonProcessingException;
import jdk.internal.misc.InnocuousThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
@RestController
public class PicturePokerGame {
    public static void main (String[]args){
        SpringApplication.run(PicturePokerGame.class, args);
    }
}
