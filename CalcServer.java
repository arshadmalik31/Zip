[Save Below code as Calc.idl]
module CalcApp 
{
interface Calc
{
exception DivisionByZero {};
float sum(in float a, in float b);
float div(in float a, in float b) raises (DivisionByZero);
float mul(in float a, in float b);
float sub(in float a, in float b);
};
};




[Save Below code as CalcServer.java]
import CalcApp.*;
import CalcApp.CalcPackage.DivisionByZero;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import java.util.Properties;
class CalcImpl extends CalcPOA {
@Override
public float sum(float a, float b) {
return a + b;
}
@Override
public float div(float a, float b) throws DivisionByZero {
if (b == 0) {
throw new CalcApp.CalcPackage.DivisionByZero();
} else {
return a / b;
}
}
@Override
public float mul(float a, float b) {
return a * b;
}
@Override
public float sub(float a, float b) {
return a - b;
}
private ORB orb;
public void setORB(ORB orb_val) {
orb = orb_val;
}
}
public class CalcServer {
public static void main(String args[]) {
try {
// create and initialize the ORB
ORB orb = ORB.init(args, null);
// get reference to rootpoa & activate the POAManager
POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
rootpoa.the_POAManager().activate();
// create servant and register it with the ORB
CalcImpl helloImpl = new CalcImpl();
helloImpl.setORB(orb);
// get object reference from the servant
org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
Calc href = CalcHelper.narrow(ref);
// get the root naming context
// NameService invokes the name service
org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
// Use NamingContextExt which is part of the Interoperable
// Naming Service (INS) specification.
NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
// bind the Object Reference in Naming
String name = "Calc";
NameComponent path[] = ncRef.to_name(name);
ncRef.rebind(path, href);
System.out.println("Ready..");
// wait for invocations from clients
orb.run();
} catch (Exception e) {
System.err.println("ERROR: " + e);
e.printStackTrace(System.out);
}
System.out.println("Exiting ...");
}
}






At 1st Console
1) idlj -fall Calc.idl
2) javac *.java CalcApp/*.java
   OR
   javac -Xlint .java CalcApp/.java
3) orbd -ORBInitialPort 1050&
At 2nd Console
1) javac CalcServer.java
2) java CalcServer -ORBInitialPort 1050&
At 3rd Console
1) javac CalcClient.java
2) java CalcClient -ORBInitialPort 1050
