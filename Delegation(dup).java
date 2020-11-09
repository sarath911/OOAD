class Delegation {
	
	public static void main(String args[]) {
		B b = new B();
		System.out.println(b.f()+b.g()+b.p(1)+b.q(2));
	
		D d = new D();
		System.out.println(d.f()+d.g()+d.h()+d.p(1)+d.q(2)+d.r());

		B2 b2 = new B2();
		System.out.println(b2.f()+b2.g()+b2.p(1)+b2.q(2));	
		
		D2 d2 = new D2();
		System.out.println(d2.f()+d2.g()+d2.h()+d2.p(1)+d2.q(2)+d2.r());	
		
	}
}

class Delegation2 {
		
	public static void main(String args[]) {
		
		E e = new E();
		System.out.println(e.f()+e.g()+e.h()+e.p(1)+e.q(2)+e.r()+e.k(100));
		
		F f = new F();
		System.out.println(f.f()+f.g()+f.h()+f.p(1)+f.q(2)+f.r()+f.j(10)+f.l(100));

		E2 e2 = new E2();
		System.out.println(e2.f()+e2.g()+e2.h()+e2.p(1)+e2.q(2)+e2.r()+e2.k(100));

		F2 f2 = new F2();
		System.out.println(f2.f()+f2.g()+f2.h()+f2.p(1)+f2.q(2)+f2.r()+f2.j(10)+f2.l(100));	
		
	}
}


abstract class A {
    public static int a1 = 100;
	protected int a2 = 200;

	public int f() {
		return u(100) + v(200);
	}

	public int u(int m) {return m; }

    public int v(int m) {return m*2; }
	
}

class B extends A {
	public static int b1 = 1000;
	protected int b2 = 2000;

	public int g() {
		return this.p(100) + this.q(200); 
	}

	public int p(int m) {
		return m + a1+b1;
	}

	public int q(int m) {
		return m + a2+b2;
	}
}

abstract class C extends B {
	public static int c1 = 10000;
	protected int c2 = 20000;

	public int r() {
		return f() + g() + h();
	}

	public int q(int m) {
		return m + a2 + b2 + c2;
	}

	protected abstract int h();
}

class D extends C {
	public static int d1 = 100000;
	protected int d2 = 200000;

	public int r() {
		return f() + g() + this.h();
	}

	public int p(int m) {
		return super.p(m) + d2;
	}

	public int h() {
		return a1 + b1 + c1;
	}
	
	public int j(int n) {
		return r() + super.r();
	}

}

class E extends C {
	protected int e1 = 1;
	protected int e2 = 2;

	public int q(int m) {
		return p(m) + c2;
	}

	public int h() {
		return a1 + b1 + e1;
	}
	
	public int k(int n) {
		return q(n) + super.q(n);
	}

}

class F extends D {
	protected int f1 = 10;
	protected int f2 = 20;

	public int q(int m) {
		return p(m) + c1 + d1;
	}

	public int h() {
		return c2 + f2;
	}
	
	public int l(int n) {
		return q(n) + super.q(n);
	}

}



//============= TRANSLATION IN TERMS OF DELEGATION ===============

interface IA {   
         // fill in the details
	default int f() {return u(100) + v(200);};
	
	default int u(int m) {return m; }

    default int v(int m) {return m*2; }
	
    int a1 = 100;
	int a2 = 200;
	
}

interface IB extends IA {
		
	default int g() {return this.p(100) + this.q(200);}

	default int p(int m) {return m + a1+b1;}

	default int q(int m) {return m + a2+b2;}
	
	int b1 = 1000;
	int b2 = 2000;
}

interface IC extends IB {
	// fill in the details
	int c1 = 10000;
	int c2 = 20000;

	default int r() {return f() + g() + this.h();}
	
	default int q(int m) {return m + a2 + b2 + c2;}

	int h();
}

interface ID extends IC {
	// fill in the details
	int d1 = 100000;
	int d2 = 200000;

	default int r() {return f() + g() + this.h();}

	int p(int m);

	default int h() {return a1 + b1 + c1;}
	
	default int j(int n) {return r() + this.r();}

}

interface IE extends IC {
	// fill in the details
	
	int e1 = 1;
	int e2 = 2;

	default int q(int m) {return p(m) + c2;}

	default int h() {return a1 + b1 + e1;}
	
	int k(int n);
	
}

interface IF extends ID {
	// fill in the details
	int f1 = 10;
	int f2 = 20;

	default int q(int m) {return p(m) + c1 + d1;}

	default int h() {return c2 + f2;}
	
	int l(int n);
}

class A2 implements IA {
	
	// fill in the details
	
	IA this2;
	
	public A2(IA a) {this2=a;}
	    
}

class B2 implements IB {
	
	// fill in the details
	
	A2 super2;
	IB this2;
	
	public B2() {
		super2=new A2(this);
		this2=this;
	}
	
	public B2(IB e) {
		super2 = new A2(e); 
		this2 = e;
	}
	
}

 class C2 implements IC {

	// fill in the details
	
	B2 super2;
	IC this2;
	
	public C2(IC e) {
		super2 = new B2(e); 
		this2 = e;
	}

	public int h() {
		return this2.h();
	}
		
}

class D2 implements ID {

	// fill in the details
	
	C2 super2;
	ID this2;
	
	public D2() {
		super2= new C2(this);
		this2=this;
	}
	
	
	public D2(ID e) {
		super2 = new C2(e); 
		this2 = e;
	}

	public int p(int m) {return super2.super2.p(m) + d2;}	

}

class E2 implements IE {
	// fill in the details
	
	C2 super2;
	IE this2;
	
	public E2() {
		super2= new C2(this);
		this2=this;
	}

	public int k(int n) {return this2.q(n) + super2.q(n);}

}

class F2 implements IF{
	// fill in the details
	
	D2 super2;
	IF this2;
	
	public F2() {
		super2= new D2(this);
		this2=this;
	}

	public int p(int m) {return super2.p(m);}

	public int l(int n){return q(n) + super2.super2.q(n);}
	
}
