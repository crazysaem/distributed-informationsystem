package rwi.infosystem.internal.initiator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Unzip {

	public static final void copyInputStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

	public static void unrar(String file) {
		Enumeration entries;
		ZipFile zipFile;

		try {
			File nF = new File(file);
			String folder = nF.getAbsolutePath().substring(0,
					nF.getAbsolutePath().lastIndexOf("\\"))
					+ "\\";
			zipFile = new ZipFile(nF);

			entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				if (entry.isDirectory()) {
					// Assume directories are stored parents first then
					// children.
					System.err.println("Extracting directory: "
							+ entry.getName());
					// This is not robust, just for demonstration purposes.
					(new File(folder + entry.getName())).mkdir();
					continue;
				}

				System.err.println("Extracting file: " + entry.getName());
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(folder
								+ entry.getName())));
			}

			zipFile.close();
		} catch (IOException ioe) {
			System.err.println("Unhandled exception:");
			ioe.printStackTrace();
			return;
		}
	}

	
	public static void saveUrl(String filename, String urlString) throws MalformedURLException, IOException
    {
    	BufferedInputStream in = null;
    	FileOutputStream fout = null;
    	try
    	{
    		in = new BufferedInputStream(new URL(urlString).openStream());
    		File f =  new File(filename);
    		f.setWritable(true);		
    		if(!f.getParentFile().exists()){
    			f.getParentFile().mkdirs();
    		}
    		fout = new FileOutputStream(f);

    		byte data[] = new byte[1024];
    		int count;
    		while ((count = in.read(data, 0, 1024)) != -1)
    		{
    			fout.write(data, 0, count);
    		}
    	}
    	finally
    	{
    		if (in != null)
    			in.close();
    		if (fout != null)
    			fout.close();
    	}
    }
	
	public static void replaceInFile(String filename,String newport){
		try
        {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "", oldtext = "";
        while((line = reader.readLine()) != null)
            {
            oldtext += line + "\r\n";
        }
        reader.close();
        // replace a word in a file
        //String newtext = oldtext.replaceAll("drink", "Love");
       
        //To replace a line in a file
        int istart = oldtext.indexOf("-Dorg.osgi.service.http.port=");
        int ilength = "-Dorg.osgi.service.http.port=XXXX".length();
        String oldsub = oldtext.substring(istart,istart+ilength);
        String newsub = oldsub.substring(0, oldsub.length()-4)+newport;
        
        String newtext = oldtext.replaceAll(oldsub, newsub);
       
        FileWriter writer = new FileWriter(filename);
        writer.write(newtext);
        writer.close();
    }
    catch (IOException ioe)
        {
        ioe.printStackTrace();
    }
		
	}
}
