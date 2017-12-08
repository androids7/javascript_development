/****************************

 Class Name EditActivity
 Author Mix Angel
 QQ 1948416296
 Activity 代码编辑器主界面

 ****************************/
package as.mke.jscilent;
import android.os.*;
import android.view.*;
import android.widget.*;
import as.mke.jscilent.View.*;
import java.io.*;

import android.support.v4.view.ViewPager;
import java.util.*;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.*;
import android.content.*;
import android.app.*;
import as.mke.jscilent.Adapter.BottomAdapter;
import android.graphics.drawable.*;
import com.jide.*;
import as.mke.jscilent.File.*;
import as.mke.jscilent.Util.*;
public class EditActivity extends BaseActivity 
{
    private MEditText mEditText;
    private String mResult = "";
    private String mFileName = "";
    private LinearLayout mLayout;
    private boolean mCanEdit;
    private PopupWindow mBottom,mCreate;
    private ViewGroup mPopup,mPopup2;
    private ViewPager mPager;
    private ListView mFileList;
    private TextView mPrint;
    private TextView mPath;
    private ImageView mMore;
    
    // 记录当前的父文件夹
    private File FILE;
    // 记录当前路径下的所有文件夹的文件数组
    File[] currentFiles;
    
    View mPagerOne;
    View mPagerTwo;
   
	private String nowpath="/sdcard/JIDE";
	
	private Button mCreate_file,mCreate_back,mCreate_project;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){ 
			//透明状态栏 
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); 
			//透明导航栏 
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); 
		} 
		
        setContentView(R.layout.activity_edit);
        //  初始化目录和解压资源
        MixPath.load();
        if(mPreferences.getBoolean("isFirstRun",false) == false){
            MixPath.createDir();
            MixFile.unPack(EditActivity.this,"code.html","模版.html",MixPath.getRootPath());
         //   MixFile.unPack(EditActivity.this,"android.js","android.js",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/helloworld.html","你好世界.html",MixPath.getRootPath()+"例程/");
			
            MixFile.unPack(EditActivity.this,"code/alerthello","弹出框hello.html",MixPath.getRootPath()+"例程/");
			
			MixFile.unPack(EditActivity.this,"code/toast.html","弹出提示hello.html",MixPath.getRootPath()+"例程/");
			
			MixFile.unPack(EditActivity.this,"code/readfile.html","读取文件内容.html",MixPath.getRootPath()+"例程/");
			MixFile.unPack(EditActivity.this,"code/writefile.html","写入文件内容.html",MixPath.getRootPath()+"例程/");
			
			MixFile.unPack(EditActivity.this,"code/listfile.html","获取文件列表.html",MixPath.getRootPath()+"例程/");
			
			
			MixFile.unPack(EditActivity.this,"code/jquery.html","jquery实例.html",MixPath.getRootPath()+"例程/");
			
			
			/*
            MixFile.unPack(EditActivity.this,"code/findfile.c","文件查找.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/player.c","内置播放器.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/helloworld.c","helloworld.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/helloworld.h","helloworld.h",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/date.c","时间与日期.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/wjdx.c","文件读写.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/pmzb.c","屏幕坐标.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/paint.c","简单画图.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/jjcfb.c","九九乘法表.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/1.bmp","1.bmp",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"musicbg.mp4","MusicBackground.mp4",MixPath.getRootPath()+".MIDE/");
			*/
            Toast.makeText(this,"资源解压到"+MixPath.getRootPath()+"成功！",Toast.LENGTH_SHORT).show();
            mEditor.putBoolean("isFirstRun",true).commit();
        }
        if(!new File(MixPath.getRootPath()).exists())
        {
            MixPath.createDir();
            MixFile.unPack(EditActivity.this,"code.html","模版.html",MixPath.getRootPath());
        //    MixFile.unPack(EditActivity.this,"android.js","android.js",MixPath.getRootPath()+"例程");
            MixFile.unPack(EditActivity.this,"code/helloworld.html","你好世界.html",MixPath.getRootPath()+"例程/");
			
			MixFile.unPack(EditActivity.this,"code/alerthello","弹出框hello.html",MixPath.getRootPath()+"例程/");
			MixFile.unPack(EditActivity.this,"code/toast.html","弹出提示hello.html",MixPath.getRootPath()+"例程/");
			MixFile.unPack(EditActivity.this,"code/readfile.html","读取文件内容.html",MixPath.getRootPath()+"例程/");
			
			MixFile.unPack(EditActivity.this,"code/writefile.html","写入文件内容.html",MixPath.getRootPath()+"例程/");
			MixFile.unPack(EditActivity.this,"code/listfile.html","获取文件列表.html",MixPath.getRootPath()+"例程/");
			
			MixFile.unPack(EditActivity.this,"code/jquery.html","jquery实例.html",MixPath.getRootPath()+"例程/");
			
			
			/*
            MixFile.unPack(EditActivity.this,"code/data.c","数据存储.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/findfile.c","文件查找.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/player.c","内置播放器.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/helloworld.c","helloworld.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/helloworld.h","helloworld.h",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/date.c","时间与日期.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/wjdx.c","文件读写.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/pmzb.c","屏幕坐标.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/paint.c","简单画图.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/jjcfb.c","九九乘法表.c",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"code/1.bmp","1.bmp",MixPath.getRootPath()+"例程/");
            MixFile.unPack(EditActivity.this,"musicbg.mp4","MusicBackground.mp4",MixPath.getRootPath()+".MIDE/");
			*/
            Toast.makeText(this,"资源解压到"+MixPath.getRootPath()+"成功！",Toast.LENGTH_SHORT).show();
        }
		/*
        if(!new File(MixPath.getRootPath()+".MIDE/MusicBackground.mp4").exists()){
            MixFile.unPack(EditActivity.this,"musicbg.mp4","MusicBackground.mp4",MixPath.getRootPath()+".MIDE/");
        }
		*/
        initView();
    }
    
    // 初始化控件，处理事件
    public void initView()
    {
        mEditText = (MEditText) findViewById(R.id.editEditText);
        mLayout = (LinearLayout) findViewById(R.id.editLinearLayout);
        mCanEdit = false; 

        mPopup = (ViewGroup)getLayoutInflater().inflate(R.layout.edit_bottom,null);
		mPopup2=(ViewGroup)getLayoutInflater().inflate(R.layout.create_bottom,null);
		mCreate=new PopupWindow(mPopup2,LayoutParams.WRAP_CONTENT,800);
        mBottom = new PopupWindow(mPopup, LayoutParams.FILL_PARENT, 800);
        mPager = (ViewPager) mPopup.findViewById(R.id.bottom_ViewPager);
		
		
		
		
		
        //这个集合里的每一个元素就代表一页
        List<View> views = new ArrayList<View>();
        //为集合添加值
        LayoutInflater lf = getLayoutInflater().from(this);
        mPagerOne = lf.inflate(R.layout.edit_file_bottom, null);
        mPagerTwo = lf.inflate(R.layout.edit_print_bottom, null);
        mFileList = (ListView) mPagerOne.findViewById(R.id.filelist_list);

        mFileList.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> p1,View p2, int p3, long p4)
                {
                    // 如果用户单击了文件，则打开文件
                    if (currentFiles[p3].isFile()) {
                        String type = getFileType(currentFiles[p3].toString());
                        if(type.equals(".JS")||type.equals(".js")||type.equals(".TXT")||type.equals(".txt")||type.equals(".HTML")||type.equals(".html")||type.equals(".css")||type.equals(".CSS"))
                        {
                            mFileName = currentFiles[p3].toString();
                            mEditor.putString("historyDir", currentFiles[p3].getParent()).commit();
                            readFile(mFileName);
                        }
                        if(type.equals(".MP3")||type.equals(".mp3")||type.equals(".M4A")||type.equals(".m4a"))
                        {
                            Intent into = new Intent();
                            into.setClass(EditActivity.this, MusicActivity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("path", currentFiles[p3].toString());
                            mBundle.putInt("loop", 0);
                            into.putExtras(mBundle);
                            startActivity(into);
                        }
                        if(type.equals(".MP4")||type.equals(".mp4")||type.equals(".AVI")||type.equals(".avi")||type.equals(".3GP")||type.equals(".3gp"))
                        {
                            Intent into = new Intent();
                            into.setClass(EditActivity.this, VideoActivity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("path", currentFiles[p3].toString());
                            into.putExtras(mBundle);
                            startActivity(into);
                        }
                    }
                    else if(currentFiles[p3].isDirectory()){

                        // 获取用户点击的文件夹 下的所有文件
                        File[] tem = currentFiles[p3].listFiles();
                        if (tem == null || tem.length == 0) {
                            Toast.makeText(EditActivity.this,
                                           "无法打开|没有文件", Toast.LENGTH_LONG).show();
                        } else {
                            // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                            FILE = currentFiles[p3];
                            // 保存当前的父文件夹内的全部文件和文件夹
                            currentFiles = tem;
                            // 再次更新ListView
                            inflateListView(currentFiles);
                        }
                    }
                }

            });
        mFileList.setOnItemLongClickListener(new OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
                {
					
					
					final int postion=p3;
					
					TextView reName,delete,copypath;
					
					
                    ViewGroup mFileView = (ViewGroup)getLayoutInflater().inflate(R.layout.filelist_box, null);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditActivity.this);
                    mBuilder.setView(mFileView);
                  final Dialog dialog=mBuilder.create();
					dialog.show();
					
					reName=(TextView)mFileView.findViewById(R.id.reName);
					delete=(TextView)mFileView.findViewById(R.id.delete);
					copypath=(TextView)mFileView.findViewById(R.id.copyPath);
					
					delete.setOnClickListener(new OnClickListener()
						{
							public void onClick(View v)
							{
								/*
								
								if (currentFiles[postion].isFile())
								{
									*/
										mFileName = currentFiles[postion].toString();
									MixFile.deleteFile(mFileName);
									refslist();
									
								//}
								//Toast.makeText(EditActivity.this,id,0).show();
								dialog.dismiss();
							}
						});
					
						
					reName.setOnClickListener(new OnClickListener()
						{
							public void onClick(View v)
							{
								dialog.dismiss();
								ViewGroup mFileView2 = (ViewGroup)getLayoutInflater().inflate(R.layout.rename, null);
								AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(EditActivity.this);
								mBuilder2.setView(mFileView2);
								final  AlertDialog dialog2= mBuilder2.create();
								dialog2.show();
								Button cancel,create;
								final EditText edit2=(EditText)mFileView2.findViewById(R.id.renameEditText1);
								cancel=(Button)mFileView2.findViewById(R.id.renameButton2);
								create=(Button)mFileView2.findViewById(R.id.renameButton1);
								
								
								cancel.setOnClickListener(new OnClickListener()
									{
										public void onClick(View v)
										{
											dialog2.dismiss();
											

										}
									});
								
								create.setOnClickListener(new OnClickListener()
									{
										public void onClick(View v)
										{
											
											mFileName = currentFiles[postion].toString();
											File file2=new File(mFileName);
										if(file2.exists()&&!edit2.getText().equals(""))
											{
												try
												{
													
												
												File mBack = new File(FILE.getCanonicalPath() +"/"+ edit2.getText().toString());
											if(mBack.exists())
											{
												Toast.makeText(EditActivity.this,"文件名已经存在！",0).show();
											}
											else{
												
											
													file2.renameTo(mBack);
												Toast.makeText(EditActivity.this,"重命名成功",0).show();
												
													dialog2.dismiss();
												}
												
												}
												catch (IOException e)
												{}
											}
											
											else
											{
												Toast.makeText(EditActivity.this,"输入不能为空",0).show();
											}
											
											
										}
									});
								
									
									
								
							}
						});
						
					copypath.setOnClickListener(new OnClickListener()
						{
							
							public void onClick(View v)
							{
							ClipboardManager clipboard = null;
								clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
								
								ClipData textCd = ClipData.newPlainText("path", currentFiles[postion].toString());
								clipboard.setPrimaryClip(textCd);
								
								dialog.dismiss();
							}
						});
						
					
                    return true;
                }
            });
        mFileList.setSelection(0);
        views.add(mPagerOne);
        views.add(mPagerTwo);
        mPager.setAdapter(new BottomAdapter(views));
        mBottom.setFocusable(true);
		mCreate.setFocusable(true);
        //必须设置背景  
        mBottom.setBackgroundDrawable(new BitmapDrawable());
        //设置点击其他地方就消失
        mBottom.setOutsideTouchable(true);
		
		mCreate.setOutsideTouchable(true);
		
        mPrint = (TextView) mPagerTwo.findViewById(R.id.TextView_Print);
        mPath = (TextView) mPagerOne.findViewById(R.id.filelist_title);
        mMore = (ImageView) mPagerOne.findViewById(R.id.filelist_more);
		
		mCreate_file=(Button)mPopup2.findViewById(R.id.createbottomFile);
		mCreate_back=(Button)mPopup2.findViewById(R.id.createbottomback);
		mCreate_project=(Button)mPopup2.findViewById(R.id.createbottomProject);
		
		
		 
		//显示更多
        mMore.setOnClickListener(new View.OnClickListener(){

				
                @Override
                public void onClick(View v)
                {
					try
					{
						/*
					PopupMenu popmenushow=new PopupMenu(EditActivity.this,v);
					
					popmenushow.inflate(R.menu.popshowmemu);
					
					popmenushow.show();
					*/
					mCreate.showAtLocation(mEditText,Gravity.BOTTOM,0,0);
					}catch(Exception e)
					{
						Toast.makeText(EditActivity.this,e.toString(),0).show();
					}
					
					/*
                    File mBack = new File(FILE.getParent());
                    currentFiles = mBack.listFiles();
                    inflateListView(currentFiles);
					*/
                }
            });
			
			
			
			
			
		mPath.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v)
                {
				

					
					 File mBack = new File(FILE.getParent());
					 currentFiles = mBack.listFiles();
					 inflateListView(currentFiles);
					 
                }
            });
			
			
			
		mCreate_file.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v)
                {



					ViewGroup mFileView = (ViewGroup)getLayoutInflater().inflate(R.layout.new_file_dialog, null);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditActivity.this);
                    mBuilder.setView(mFileView);
                  final  AlertDialog dialog= mBuilder.create();
					dialog.show();
					Button cancel,create;
					final EditText medittext;
					final CheckBox checkbox=(CheckBox)mFileView.findViewById(R.id.id_new_file_dialog_check);
					
					cancel=(Button)mFileView.findViewById(R.id.id_new_file_dialog_cancel);
					create=(Button)mFileView.findViewById(R.id.id_new_file_dialog_create);
					
					medittext=(EditText)mFileView.findViewById(R.id.id_new_file_dialog_path);
					
					
					cancel.setOnClickListener(new OnClickListener()
					{
						public void onClick(View v)
						{
							dialog.dismiss();
						}
					});
					
					
					create.setOnClickListener(new OnClickListener()
						{
							public void onClick(View v)
							{
								String str=medittext.getText().toString();
								
								if(str.length()<0||str==null||str.equals(""))
								{
									Toast.makeText(EditActivity.this,"输入不能为空！",0).show();
								}
								else
								{
									try
									{
										
										if(checkbox.isChecked())
										{
										File file=new File(FILE.getCanonicalPath() +"/"+ str);
										if(file.exists())
										{
											Toast.makeText(EditActivity.this,"文件夹已经存在!无法创建",0).show();
										}
										else
										{
											file.mkdir();
											dialog.dismiss();
											refslist();
										}
										
										}
										
										else
										{
											File file=new File(FILE.getCanonicalPath() + "/"+str);
											if(file.exists())
											{
												Toast.makeText(EditActivity.this,"文件已经存在!无法创建",0).show();
											}
											
											else
											{
												file.createNewFile();
												dialog.dismiss();
												refslist();
											}
											
											
										}
										
									}
									catch (Exception e)
									{
										Toast.makeText(EditActivity.this,e.toString(),0).show();
										
									}
								}
							}
						});
					
					
				}
				
            });
			
			
		mCreate_project.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v)
                {

//新建工程
					
					
				
					ViewGroup mFileView = (ViewGroup)getLayoutInflater().inflate(R.layout.new_project_dialog, null);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditActivity.this);
                    mBuilder.setView(mFileView);
                    Dialog dialog=mBuilder.create();
					dialog.show();
					EditText appname,packagename,appicon,versionstring,versioncode,msgabout;
					Button cancel,create;
					
					
					cancel=(Button)mFileView.findViewById(R.id.newprojectdialogcancel);
					create=(Button)mFileView.findViewById(R.id.newprojectdialogcreate);

					appname=(EditText)mFileView.findViewById(R.id.newprojectdialogEditTextappName);
					packagename=(EditText)mFileView.findViewById(R.id.newprojectdialogEditTextpackagename);
					appicon=(EditText)mFileView.findViewById(R.id.newprojectdialogEditTextappicon);
					versionstring=(EditText)mFileView.findViewById(R.id.newprojectdialogEditTextversionstring);
					versioncode=(EditText)mFileView.findViewById(R.id.newprojectdialogEditTextappName);
					msgabout=(EditText)mFileView.findViewById(R.id.newprojectdialogEditTextappName);
					
					
					
                }
            });
			
		mCreate_back.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v)
                {

					
					
					File mBack=new File(FILE.getParent());
					
					FILE=mBack;
					currentFiles = mBack.listFiles();
				//	Toast.makeText(EditActivity.this,currentFiles.toString(),0).show();
					inflateListView(currentFiles);
					
					
                }
            });
			
			
    }
    
	
	
	public void refslist()
	{
		File mBack = new File(FILE.getParent());
		currentFiles = mBack.listFiles();
		inflateListView(currentFiles);
	}
	
    /**
     * 根据文件夹填充ListView
     *
     * @param files
     */
    private void inflateListView(File[] files) {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		
		
		
		
        for (int i = 0; i < files.length; i++) {
           
			Map<String, Object> listItem = new HashMap<String, Object>();
			
            if (files[i].isDirectory()) {
        // 如果是文件夹就显示的图片为文件夹的图片
                listItem.put("icon", R.drawable.folder);
            } 
            else if(files[i].isFile()) {
               String type = getFileType(files[i].getAbsolutePath());
                if(type.equals(".C")||type.equals(".c"))
                {
                    listItem.put("icon", R.drawable.file_type_c);
                }
                else {
                    listItem.put("icon",R.drawable.file_type_unknown);
                }
                if(type.equals(".HTML")||type.equals(".html"))
                {
                    listItem.put("icon", R.drawable.file_type_cpp);
                }
                if(type.equals(".H")||type.equals(".h"))
                {
                    listItem.put("icon", R.drawable.file_type_h);
                }
                if(type.equals(".HTML")||type.equals(".html"))
                {
                    listItem.put("icon", R.drawable.file_type_html);
                }
                if(type.equals(".XML")||type.equals(".xml"))
                {
                    listItem.put("icon", R.drawable.file_type_xml);
                }
                if(type.equals(".TXT")||type.equals(".txt"))
                {
                    listItem.put("icon", R.drawable.file_type_txt);
                }
                if(type.equals(".JS")||type.equals(".js"))
                {
                    listItem.put("icon", R.drawable.file_type_js);
                }
                if(type.equals(".JAVA")||type.equals(".java"))
                {
                    listItem.put("icon", R.drawable.file_type_java);
                }
               
            }
        // 添加一个文件名称
		   
            listItem.put("name", files[i].getName());
			
			
			
            listItems.add(listItem);
        }
       // 定义一个SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(
            EditActivity.this, listItems, R.layout.file_list_item,
            new String[] { "name", "icon" }, new int[] {
                R.id.filelistitem_title, R.id.filelistitem_image });
        // 填充数据集
        mFileList.setAdapter(adapter);
        try {
            mPath.setText( FILE.getCanonicalPath());
        } catch (Exception e) {
            Toast.makeText(EditActivity.this,e.toString(),0).show();
        }
    }
    
    /**
     * 获取文件后缀名
     * 
     * @param fileName
     * @return 文件后缀名
     */
    public static String getFileType(String fileName) 
    {
            int last = fileName.lastIndexOf(".");
            String type = fileName.substring(last, fileName.length());
            return type;
    }
    
    // 读取文件到编辑器
    public int readFile(String mFilePath)
    {
        try
        {
            if(mFilePath.equals(""))
            {
                Toast.makeText(this, "文件路径错误！", Toast.LENGTH_SHORT).show();
                return -1;
            }
            else{
                String mFileStr = MixFile.readFile(mFileName, "UTF-8");
                mEditText.setText(mFileStr);
                return 0;
            }
        }
        catch (IOException e)
        {
            Toast.makeText(this, "读取失败", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
    
    // 保存编辑后的文件
    public int saveFile()
    {
        mResult = mEditText.getText().toString();
        try
        {
            if(mFileName.equals(""))
            {
                Toast.makeText(this, "未打开文件", Toast.LENGTH_SHORT).show();
                return -1;
            }
            else{
                MixFile.writeFile(mFileName, mResult, "UTF-8");
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        catch (IOException e)
        {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    // 设置编辑器状态
    public void setEditMode(boolean canEdit)
    {
        if(canEdit == false){
            // 不可编辑
            mEditText.setFocusable(false);
            mEditText.setFocusableInTouchMode(false);
            mCanEdit = true;
        }
        else if(canEdit == true){
            // 可编辑
            mEditText.setFocusableInTouchMode(true);
            mEditText.setFocusable(true);
            mEditText.requestFocus();
            mCanEdit = false;
        }
    }
    
    // 判断主题
    public static boolean isLightTheme() 
    {
        return true;
    }
    
    // 进入在线界面
    public void intoOnline()
    {
        Intent onOnline=new Intent();
        onOnline.setClass(this,OnlineActivity.class);
        startActivity(onOnline);
    }
    
    // 进入api界面
    public void intoApi()
    {
        Intent onApi=new Intent();
        onApi.setClass(this,ApiActivity.class);
        startActivity(onApi);
    }

    // 进入运行界面
    public void intoRun()
    {
            Intent onRun=new Intent( );
            onRun.putExtra("runfile",mFileName);//传递文件路径
            onRun.setClass(this,Emulator_js.class);
            startActivity(onRun);
    }
    
    // 进入设置界面
    public void intoSetting()
    {
        Intent onSetting=new Intent();
        onSetting.setClass(this,SettingActivity.class);
        startActivity(onSetting);
    }
    
	// 进入打包界面
    public void intoApk()
    {
        Intent onSetting=new Intent();
        onSetting.setClass(this,ApkActivity.class);
        startActivity(onSetting);
    }
    
	
	
	
    // 创建一个菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        SubMenu subRun = menu.addSubMenu(1, 1, 1, R.string.app_run);
        subRun.setIcon(isLightTheme() ? R.drawable.ic_l_play_light : R.drawable.ic_l_play_dark);
        subRun.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SubMenu subOptions = menu.addSubMenu(2, 2, 1, R.string.app_menu);
        subOptions.setIcon(isLightTheme() ? R.drawable.ic_menu_moreoverflow_normal_holo_light : R.drawable.ic_menu_moreoverflow_normal_holo_dark);
        subOptions.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        int i = 0;
        subOptions.add(2, 3, i++, R.string.app_show);
        subOptions.add(2, 4, i++, R.string.app_save);
        subOptions.add(2, 5, i++, R.string.app_run);
        subOptions.add(2, 6, i++, R.string.app_seting);
        subOptions.add(2, 7, i++, R.string.app_online);
        subOptions.add(2, 8, i++, R.string.app_about);
        subOptions.add(2, 9, i++, R.string.app_apidoc);
        subOptions.add(2,10,i++,R.string.app_makeapp);
        subOptions.add(2, 11, i++, R.string.app_exit);
        return super.onCreateOptionsMenu(menu);
    }
    
    // 菜单事件
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        switch(item.getItemId())
        {
                case 11:SysTool.exitApp();
                break;
                case 1:
					saveFile();
					intoRun();
                break;
                case 3:setEditMode(mCanEdit);
                break;
                case 4:saveFile();
                break;
                case 5:intoRun();
                break;
                case 6:intoSetting();
                break;
                case 7:intoOnline();
                break;
                case 8:new AboutActivity(this).show();
                break;
                case 9:intoApi();
                break;
                case 10:
					intoApk();
					
				break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    
    // 点击两次退出变量
    static boolean isExit; 
    
    // 处理按键事件，可能有点复杂，反正正常使用就好
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
                if(isExit && !mBottom.isShowing()) {
                    SysTool.exitApp();
                }
                else if (!isExit && !mBottom.isShowing()) {

                    isExit = true;
                    Toast.makeText(this, "再按一次退出",
                                   Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                    isExit = false;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                }
            else if(!isExit && mBottom.isShowing())
                {
                    mBottom.dismiss();
                }
            }
        if(keyCode == KeyEvent.KEYCODE_MENU)
            {
                isExit = false;
                String mHistory = mPreferences.getString("historyDir","");
                if(mHistory.equals("")){
                    // 获取历史目录不到，则浏览默认目录
                    FILE = new File(MixPath.getRootPath());
                    currentFiles = FILE.listFiles();
                    // 使用当前目录下的全部文件、文件夹来填充ListView
                    inflateListView(currentFiles);
                   
                }
                else {
                    FILE = new File(mHistory);
                    if(!FILE.exists()){
                    FILE = new File(MixPath.getRootPath());
                   } 
                    currentFiles = FILE.listFiles();
                    // 使用当前目录下的全部文件、文件夹来填充ListView
                    inflateListView(currentFiles);
                }
                //显示当前文件路径
                //mPath.setText(FILE.getAbsolutePath());
                try {
                    String mNewLogs = MixFile.readFile(MixPath.getFullName("print.txt"),"UTF-8");
                    mPrint.setText("" + mNewLogs);
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                mBottom.showAtLocation(mEditText, Gravity.BOTTOM, 0, 0);
            }
        return true;
    }
    
    
  
}
