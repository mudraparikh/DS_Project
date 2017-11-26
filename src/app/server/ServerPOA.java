package app.server;


/**
* app/server/ServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from App.idl
* Wednesday, November 8, 2017 12:33:45 AM EST
*/

public abstract class ServerPOA extends org.omg.PortableServer.Servant
 implements app.server.ServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("createRoom", new java.lang.Integer (0));
    _methods.put ("deleteRoom", new java.lang.Integer (1));
    _methods.put ("bookRoom", new java.lang.Integer (2));
    _methods.put ("getAvailableTimeSlots", new java.lang.Integer (3));
    _methods.put ("cancelBooking", new java.lang.Integer (4));
    _methods.put ("changeReservation", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // app/admin/Admin/createRoom
       {
         String adminID = in.read_string ();
         int roomNumber = in.read_long ();
         String date = in.read_string ();
         String timeSlots[] = app.admin.AdminPackage.timeSlotSequenceHelper.read (in);
         String $result = null;
         $result = this.createRoom (adminID, roomNumber, date, timeSlots);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // app/admin/Admin/deleteRoom
       {
         String adminID = in.read_string ();
         int roomNumber = in.read_long ();
         String date = in.read_string ();
         String timeSlots[] = app.admin.AdminPackage.timeSlotSequenceHelper.read (in);
         String $result = null;
         $result = this.deleteRoom (adminID, roomNumber, date, timeSlots);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // app/student/Student/bookRoom
       {
         String studentID = in.read_string ();
         String campusName = in.read_string ();
         int roomNumber = in.read_long ();
         String date = in.read_string ();
         String timeSlot = in.read_string ();
         String $result = null;
         $result = this.bookRoom (studentID, campusName, roomNumber, date, timeSlot);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // app/student/Student/getAvailableTimeSlots
       {
         String studentID = in.read_string ();
         String date = in.read_string ();
         String $result = null;
         $result = this.getAvailableTimeSlots (studentID, date);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // app/student/Student/cancelBooking
       {
         String studentID = in.read_string ();
         String bookingID = in.read_string ();
         String $result = null;
         $result = this.cancelBooking (studentID, bookingID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // app/student/Student/changeReservation
       {
         String studentID = in.read_string ();
         String bookingID = in.read_string ();
         String campusName = in.read_string ();
         int roomNumber = in.read_long ();
         String date = in.read_string ();
         String timesSlot = in.read_string ();
         String $result = null;
         $result = this.changeReservation (studentID, bookingID, campusName, roomNumber, date, timesSlot);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:app/server/Server:1.0", 
    "IDL:app/admin/Admin:1.0", 
    "IDL:app/student/Student:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Server _this() 
  {
    return ServerHelper.narrow(
    super._this_object());
  }

  public Server _this(org.omg.CORBA.ORB orb) 
  {
    return ServerHelper.narrow(
    super._this_object(orb));
  }


} // class ServerPOA
