package com.sk.spring.umgmt.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ReactiveLogController {
	
	Logger logger = LoggerFactory.getLogger(ReactiveLogController.class);
    @GetMapping(value = "/view-random-number", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getRandomNumber() {
        Random r = new Random();
        int low = 0;
        int high = 50;
        return Flux.fromStream(Stream.generate(() -> r.nextInt(high - low) + low)
            .map(s -> String.valueOf(s))
            .peek((msg) -> {
                logger.info(msg);
            }))
            .map(s -> Integer.valueOf(s))
            .delayElements(Duration.ofSeconds(1));
    }
    @GetMapping(value = "/view-log-file", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getLogFileData() {
    	return fromPath(Paths.get("C:\\Users\\shailendra.kum\\Downloads\\ELK\\winlogbeat-6.7.1-windows-x86_64\\logs\\winlogbeat"));
    }
    
    private static Flux<String> fromPath(Path path) {
		
    	return Flux.using(() -> getFileStream(path), Flux::fromStream,
				  BaseStream::close ).delayElements(Duration.ofSeconds(1));
		 
		/*
		 * try { return Flux.fromStream(Files.lines(path))
		 * .delayElements(Duration.ofSeconds(1)); } catch (IOException e) { 
		 * Auto-generated catch block e.printStackTrace(); return Flux.using(() ->
		 * Files.lines(path), Flux::fromStream, BaseStream::close
		 * ).delayElements(Duration.ofSeconds(1)); }
		 */
    }
    
 private static Flux<String> fromPath1(Path path) {
	 Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
	 Flux<String> events = null;
	 
	 
	 try {
		 BufferedReader reader = Files.newBufferedReader(path);
		 //events  = Flux.fromStream(Files.lines(path));
		 events  = Flux.fromStream(reader.lines());
	} catch (IOException e) {
		e.printStackTrace();
	}
	 return Flux.zip(events, interval, (key, value) -> key);
		 
		
    }
 
	private static Stream<String> getFileStream(Path path) throws IOException {
		BufferedReader reader = Files.newBufferedReader(path);
		return Stream.generate(() -> readLine(reader)).filter(s -> s != null && s.length() > 0);

	}
 
 
 private static String readLine(BufferedReader reader) {
		try {
			String line = null;
			line= reader.readLine();
			if(line==null ) {
				line="";
				//Thread.sleep(5);
				//readLine(reader);
				}
			return line;
		} catch (Exception e) {
			return "test";
		}
		
	}

}
