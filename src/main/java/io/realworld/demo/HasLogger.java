package io.realworld.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface HasLogger
{
	default Logger log() {
		return LoggerFactory.getLogger(this.getClass());
	}
}
