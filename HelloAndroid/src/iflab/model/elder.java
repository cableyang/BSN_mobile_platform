package iflab.model;


/*
 * contains elder information
 */
public class elder
{
	private String name;
	private int age;
	private int id;
	private String address;
	private String phone;
	private String description;
	private byte[]img;
	//private String sanitation;
	//‘§¡Ù’’∆¨
   
	public elder(int id, String name, int age, String address, String phone,String des, byte[]im)
	{
		super();
		 this.id=id;
		 this.name=name;
		 this.age=age;
		 this.address=address;
		 this.phone=phone;
		 description=des;
		 img=im;
	}
 

	public String getname()
	{
		return name;
	}
	public void setname(String name)
	{
		this.name=name;
	}
	
	public int getid()
	{
		return id;
	}
	public void setid(int id)
	{
		this.id=id;
	}
	
	public int getage()
	{
		return age;	
	}
	public void setage(int age)
	{
		this.age=age;
	}
	
	public String getaddress()
	{
		return address;	
	}
	public void setaddress(String address)
	{
		this.address=address;
	}
	
	public String getphone()
	{
		return phone;	
	}
	public void setphone(String phone)
	{
		this.phone=phone;
	}
	
	public String getdescripiton()
	{
		return description;	
	}
	public void setdescription(String des)
	{
		this.phone=des;
	}
	
	public byte[] getimg()
	{
		return img;	
	}
	public void setimg(byte [] im)
	{
		this.img=im;
	}
	
	public String toString()
	{
		return "_id=" + id + ";_name=" + name + ";age=" + age+";address="+address+";phone="+phone;
	}
	
	
}
