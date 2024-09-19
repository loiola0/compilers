package org.victorloiola.faculdade;

public class Cidade {
    private float x;
    private float y;
    private String nome;
    
    public Cidade(){
        
    };
    
    public Cidade(char nome){
        this.x = arredondar((float) (Math.random() * 101));
        this.y = arredondar((float)(Math.random() * 101));
        this.nome = nome + "";
    }
    
    public double calcularDistancia(Cidade cidade){
        float x = Math.abs(this.getX() - cidade.getX());
        float y = Math.abs(this.getY() - cidade.getY());
        
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
    
    public String getNome() {
        return this.nome;
    }

    private float arredondar(float valor) {
        return Math.round(valor * 100.0f) / 100.0f;
    }
}
