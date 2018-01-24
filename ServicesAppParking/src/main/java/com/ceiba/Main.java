
package com.ceiba;


import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@SpringBootApplication
public class Main
{

	private static final Logger log = LoggerFactory.getLogger( Main.class );


	public static void main( String[] args )
	{
		ApplicationContext context = SpringApplication.run( Main.class, args );

		String[] beansNames = context.getBeanDefinitionNames();
		Arrays.sort( beansNames );

		for( String beanName : beansNames ) {
			log.info( beanName );
		}

	}

}