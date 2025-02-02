package ejemplo;

class O {
    void metodo() {
        System.out.println("Método de la clase O");
    }
    
    void metodoClaseO() {
        System.out.println("Método de la clase O");
    }
}

class A extends O {
    void metodo() {
        System.out.println("Método de la clase A");
    }
    
    void metodoClaseA() {
        System.out.println("Método de la clase A");
    }
}

class B extends O {
    void metodo() {
        System.out.println("Método de la clase B");
    }
    
    void metodoClaseB() {
        System.out.println("Método de la clase B");
    }
}

class C extends B {
    void metodo() {
        System.out.println("Método de la clase C");
    }
    
    void metodoClaseC() {
        System.out.println("Método de la clase C");
    }
}
