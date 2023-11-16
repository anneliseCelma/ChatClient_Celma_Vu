package com.chat.client;
//classe etat de la partie
public class EtatPartieEchecs {

	private char etatEchiquier[][];
	
	public EtatPartieEchecs(char etatEchiquier){
		this.etatEchiquier=new char[8][8];
	}
	
	
	public char[][] getEtatPartieEchecs() {
		return etatEchiquier;
	}
	
	public void setEtatEtatEchiquier(char[][] etatEchiquier) {
		this.etatEchiquier=new char[8][8];
	}
	
	@Override
	public String toString() {
		StringBuilder matrice=new StringBuilder();

        for (int i = 0; i < etatEchiquier.length; i++) {
            for (int j = 0; j < etatEchiquier.length; j++) {
            	etatEchiquier[i][0] = (char) (i + '1');
            	 char[] lowerCaseLetters = {'t', 'c', 'f', 'd', 'r', 'f', 'c', 't'};
                 etatEchiquier[0][1] = lowerCaseLetters[i];
                  
               
            }
        }

        for (int i = 0; i < etatEchiquier.length; i++) {
            for (int j = 0; j < etatEchiquier[i].length; j++) {
                matrice.append(etatEchiquier[i][j]);
            }
            matrice.append("\n");
        }

        System.out.println(matrice.toString());
		return matrice.toString();
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}