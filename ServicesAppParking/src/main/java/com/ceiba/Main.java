
package com.ceiba;


import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class Main
{


	public static void main( String[] args )
	{
		ApplicationContext context = SpringApplication.run( Main.class, args );

		String[] beansNames = context.getBeanDefinitionNames();
		Arrays.sort( beansNames );

	}

}