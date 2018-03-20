package com.k2.Wiget.templateFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.k2.Util.FileUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.templateFactory.spec.TemplateImplementation;
import com.k2.Wiget.templateFactory.spec.TemplateSpecification;
import com.k2.Wiget.templateFactory.types.TemplateDef;

/**
 * The TemplateWriter writes the java source for the template assembly weget specification and implementation classes as defined by the supplied data
 * 
 * @author simon
 *
 */
public class TemplateWriter {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private TemplateFactory factory = new TemplateFactory("com.k2.Wiget.templateFactory.impl");
	private File outputFolder;
	private File archiveFolder;
	/**
	 * Create a template writer to write the template classes to the given output folder.
	 * Template writers created in this way move current versions of the template classes to an archive folder created in the output folder.
	 * 
	 * @param outputFolder	The output folder in which to write the java source code for the template assembly wigets
	 * @throws TemplateWriterException	If there is a problem with the given source location
	 */
	public TemplateWriter(File outputFolder) throws TemplateWriterException {
		validateLocation("output", outputFolder);
		archiveFolder = outputFolder.toPath().resolve("archive").toFile();
		
		logger.debug("Preparing to write templates to {} archiveing to {}", outputFolder.getAbsolutePath(), archiveFolder.getAbsolutePath());

		this.outputFolder = outputFolder;
	}
	/**
	 * Create a template writer to write the template classes to the given output folder and archive folder.
	 * Template writers created in this way move current versions of the template classes to the given archive folder.
	 * 
	 * @param outputFolder	The output folder in which to write the java source code for the template assembly wigets
	 * @param archiveFolder	The floder in which existing java source files will be backed up
	 * @throws TemplateWriterException	If there is a problem with the given source or archive locations
	 */
	public TemplateWriter(File outputFolder, File archiveFolder) throws TemplateWriterException {
		validateLocation("output", outputFolder);
		validateLocation("archive", archiveFolder);
		
		logger.debug("Preparing to write templates to {} archiveing to {}", outputFolder.getAbsolutePath(), archiveFolder.getAbsolutePath());

		this.outputFolder = outputFolder;
		this.archiveFolder = archiveFolder;
	}
	
	private void validateLocation(String locationDescription, File location) throws TemplateWriterException {
		if (location == null)
			throw new TemplateWriterException("Unable to generate source code for templates - The given {} location is null!", locationDescription);
		
		if (!location.exists())
			throw new TemplateWriterException("Unable to generate source code for templates - The {} location {} does not exist!", locationDescription, location.getAbsolutePath());
		
		if (!location.isDirectory())
			throw new TemplateWriterException("Unable to generate source code for templates - The {} location {} is not a directory!", locationDescription, location.getAbsolutePath());
		
		if (!location.canWrite())
			throw new TemplateWriterException("Unable to generate source code for templates - Unable to write to the {} location {}!", locationDescription, location.getAbsolutePath());
		
	}
	
	/**
	 * This service method generates the java source code for the given template assemblies wiget specification and implementation classes in the output location defined when this template writer was instantiated
	 * @param template	The template tofor which to generate and write the java source code
	 * @throws TemplateWriterException	If there is a problem writing the java source code to the output location or backing up the existing source for the template wiget to the archive location
	 */
	public void write(TemplateDef template) throws TemplateWriterException {
		
		if (template == null)
			throw new TemplateWriterException("Unable to generate source code for a null template!");
		
		
		String templateSpecPackageName = ClassUtil.getPackageNameFromCanonicalName(template.getName());
		String templateImplPackageName = ClassUtil.getPackageNameFromCanonicalName(template.getImplementationName());
		Path templateSpecPath = ClassUtil.packageNameToRelativePath(templateSpecPackageName);
		Path templateImplPath = ClassUtil.packageNameToRelativePath(templateImplPackageName);
		
		FileUtil.buildTree(outputFolder, templateSpecPath, templateImplPath);
		
		File templateSpecResource = outputFolder.toPath().resolve(ClassUtil.packageNameToPath(template.getName())+".java").toFile();
		archiveResource(templateSpecResource, templateSpecPath);
		TemplateAssembly<TemplateSpecification, TemplateDef> spec = factory.getAssembly(TemplateSpecification.class);
		try {
			spec.output(template, new PrintWriter(new FileWriter(templateSpecResource))).flush();
		} catch (IOException e) {
			throw new TemplateWriterException("Error writing template specification {} to {} - {}", e, template.getName(), templateSpecResource.getAbsolutePath(), e.getMessage());
		}
		
		File templateImplResource = outputFolder.toPath().resolve(ClassUtil.packageNameToPath(template.getImplementationName())+".java").toFile();
		archiveResource(templateImplResource, templateImplPath);
		TemplateAssembly<TemplateImplementation, TemplateDef> impl = factory.getAssembly(TemplateImplementation.class);		
		try {
			impl.output(template, new PrintWriter(new FileWriter(templateImplResource))).flush();
		} catch (IOException e) {
			throw new TemplateWriterException("Error writing template implementation {} to {} - {}", e, template.getImplementationName(), templateImplResource.getAbsolutePath(), e.getMessage());
		}
			
	}
	
	private void archiveResource(File resource, Path resourcePath) throws TemplateWriterException {
		if (resource.exists()) {
			FileUtil.buildTree(archiveFolder, resourcePath);
			int i = 0;
			File archiveResource = archiveFolder.toPath().resolve(resourcePath).resolve(resource.getName()+"."+i++).toFile();
			while (archiveResource.exists()) {
				archiveResource = archiveFolder.toPath().resolve(resourcePath).resolve(resource.getName()+"."+i++).toFile();
			}
			
			try {
				Files.move(resource, archiveResource);
			} catch (IOException e) {
				throw new TemplateWriterException("Unable to archive {} - {}", e, resource, e.getMessage());
			}
		}
		
	}
	
	
	
	

}
