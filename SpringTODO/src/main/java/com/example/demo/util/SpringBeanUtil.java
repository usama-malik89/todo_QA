package com.example.demo.util;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class SpringBeanUtil {

//	Merge not null is used for ensuring that we don’t get an error when we do our update statement. 
//	We need to create a custom error handler that we can use.

	// constructor that take the data source in and data target
	public static void mergeNotNull(Object source, Object target) {
		copyProperties(source, target, getNullPropertyName(source));
	}

	private static String[] getNullPropertyName(Object source) {
		final BeanWrapper wrappedSourceObject = new BeanWrapperImpl(source);
		// loop though our data that gets passed.
		Set<String> propertyNames = new HashSet<>();
		for (PropertyDescriptor propertyDescriptors : wrappedSourceObject.getPropertyDescriptors()) {
			if (wrappedSourceObject.getPropertyValue(propertyDescriptors.getName()) == null)
				propertyNames.add(propertyDescriptors.getName());
		}
		return propertyNames.toArray(new String[propertyNames.size()]);
	}

	// this just checks our object is not null during merge and prevents spring
	// breaking.

}
