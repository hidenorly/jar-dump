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

public class JarDump {
	public static void main(String[] args) {

		Vector<OptParseItem> options = new Vector<OptParseItem>();
		options.add( new OptParseItem("-c", "--classOnly", false, "false", "Specify if you want to output .class only") );

		OptParse opt = new OptParse( args, options, "JarDump [options] hoge.jar");

		String filterRegExp = opt.values.get("-c").equals("false") ? "" : ".*\\.class";

		for(int i=0, c=opt.args.size(); i<c; i++){
			String thePath = opt.args.get(i);
			List<String> paths = FileLister.getFileList( thePath, filterRegExp );

			for( String aPath : paths ){
				System.out.println( aPath );
			}
		}
	}
}