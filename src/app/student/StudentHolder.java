package app.student;

/**
* app/student/StudentHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from App.idl
* Tuesday, November 28, 2017 11:34:48 PM EST
*/

public final class StudentHolder implements org.omg.CORBA.portable.Streamable
{
  public app.student.Student value = null;

  public StudentHolder ()
  {
  }

  public StudentHolder (app.student.Student initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = app.student.StudentHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    app.student.StudentHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return app.student.StudentHelper.type ();
  }

}
