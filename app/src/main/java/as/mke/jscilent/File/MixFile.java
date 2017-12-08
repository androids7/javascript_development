package as.mke.jscilent.File;

import java.io.*;
import android.widget.*;
import android.content.*;
import android.app.*;

import android.content.res.*;
import android.util.*;
import org.apache.http.util.EncodingUtils;
import android.content.pm.*;

public class MixFile
{
   
   public static String readAssetsFile(Activity mActivity,String name)
   {
      String str=null;
      AssetManager assetManager = mActivity.getResources().getAssets();
      try
      {
         InputStream inputStream = assetManager.open(name);
         byte[] arrby = new byte[inputStream.available()];
         inputStream.read(arrby);
         str=EncodingUtils.getString(arrby,"UTF-8");
      }
      catch (IOException e)
      {
         Toast.makeText(mActivity,"读取失败！",Toast.LENGTH_SHORT).show();
      }
      return str;
   }
   
   // 路径，内容，编码
    public static void writeFile(String path, String content, String encoding)
    throws IOException {
        File file = new File(path);
        file.delete();
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                                                       new FileOutputStream(file), encoding));
        writer.write(content);
        writer.close();
    }
    
	// 路径，编码
    public static String readFile(String path, String encoding) throws IOException {
        String content = "";
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                                       new FileInputStream(file), encoding));
        String line = null;
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }
        reader.close();
        return content;
    }
    
    //解包assets下文件到SD卡
    //context上下文对象
    //string是assets下文件名
    //fileName是复制到sd卡文件名
    //sdDir位于sd的目录
    public static void unPack(Context context, String string,String fileName,String sdDir) {
        String string3 = sdDir+fileName;
        AssetManager assetManager = context.getResources().getAssets();
        try {

            InputStream inputStream = assetManager.open(string);
            byte[] arrby = new byte[inputStream.available()];
            inputStream.read(arrby);

            File file = new File(string3);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(arrby);
            fileOutputStream.close();
            inputStream.close();
            return;
        }
        catch (IOException var) {
            return;
        }
    }
	
	
	public static void deleteFile(String path)
	{
		File file = new File(path);
		file.delete();
	}

}
