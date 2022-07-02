/*
  Copyright (C) 2022 hidenorly

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

package com.github.hidenorly.jardump;

import com.github.hidenorly.jardump.OptParse;
import com.github.hidenorly.jardump.OptParse.OptParseItem;
import com.github.hidenorly.jardump.FileLister;

import java.io.IOException;
import java.util.Vector;
import java.util.List;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class JarDump {
	protected static void doExtract(Path srcPath, String destPath) {
		OutputStream os = null;
		InputStream is = null;
		try{
			os = new FileOutputStream( destPath );
			is =  Files.newInputStream( srcPath );
			int nReadBytes = 0;
			while( nReadBytes!=-1 ){
	      byte[] buf = new byte[1024];
	      nReadBytes = is.read( buf );
				os.write( buf, 0, nReadBytes );
			}
		} catch(Exception ex) {

		}
		try{
			if( is != null ){ is.close(); is = null;}
			if( os != null ){ os.close(); os = null;}
		} catch(Exception ex) {
		}
	}

	protected static void ensureDirectory(String path){
		try{
			String basePath = new File(path).getParent();
			Files.createDirectories( Paths.get( basePath ) );
		} catch(Exception ex) {
		}
	}

	protected static String getOutputPath(Path basePath, String outPath){
		return outPath + basePath.toString();
	}

	public static void main(String[] args) {
		Vector<OptParseItem> options = new Vector<OptParseItem>();
		options.add( new OptParseItem("-c", "--classOnly", false, "false", "Specify if you want to output .class only") );
		options.add( new OptParseItem("-e", "--extract", false, "false", "Specify if you want to extract the content") );
		options.add( new OptParseItem("-o", "--output", true, ".", "Specify the output path") );

		OptParse opt = new OptParse( args, options, "JarDump [options] hoge.jar");

		String filterRegExp = opt.values.get("-c").equals("false") ? "" : ".*\\.class";

		for(int i=0, c=opt.args.size(); i<c; i++){
			String thePath = opt.args.get(i);
			List<Path> paths = FileLister.getFileList( thePath, filterRegExp );

			boolean isExtract = opt.values.get("-e").equals("true") ? true : false;
			String outPath = opt.values.get("-o");

			for( Path aPath : paths ){
				System.out.println( aPath.toString() );
				if( isExtract ){
					String dstPath = getOutputPath( aPath,  outPath );
					ensureDirectory( dstPath );
					doExtract( aPath, dstPath );
				}
			}
		}
	}
}