package sobornov.taskone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class TaskOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskOneApplication.class, args);
	}

	@RequestMapping(value = "/")
	public String getTicket(@RequestParam(value = "ticket", defaultValue = "111111") int ticket) {
		ticketOutput(ticket, isMyTicketLucky(ticket));
		return "Get ticket was called with " + ticket + ". isMyTicketLucky: " + isMyTicketLucky(ticket);
	}


	public boolean isMyTicketLucky(int number){
		ArrayList<Integer> numbers = new ArrayList<>();

		while(number > 0) {
			numbers.add(number%10);
			number /= 10;
		}

		if (numbers.stream().limit(numbers.size()/2).reduce((s1,s2)->s1+s2).equals(numbers.stream().skip(numbers.size()/2).reduce((s1,s2)->s1+s2)))
			return true;
		return false;

	}

	public void ticketOutput(int ticket, boolean str) {
		try (BufferedWriter bw = new BufferedWriter(
					 new FileWriter( "output.csv", true
					 ))) {
				bw.write(ticket + ", " + str);
				bw.newLine();
				bw.flush();


		} catch (IOException ex) {

		}
	}
}

