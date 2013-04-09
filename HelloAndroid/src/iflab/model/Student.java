package iflab.model;


/**
 * @author coolszy
 * @blog http://blog.csdn.net/coolszy
 */
public class Student
{
	private int sid;
	private String name;
	private short age;

	public Student()
	{
		super();
	}

	public Student(int sid, String name, short age)
	{
		super();
		this.sid = sid;
		this.name = name;
		this.age = age;
	}

	public int getSid()
	{
		return sid;
	}

	public void setSid(int sid)
	{
		this.sid = sid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public short getAge()
	{
		return age;
	}

	public void setAge(short age)
	{
		this.age = age;
	}

	@Override
	public String toString()
	{
		return "sid=" + sid + ";name=" + name + ";age=" + age;
	}
}
