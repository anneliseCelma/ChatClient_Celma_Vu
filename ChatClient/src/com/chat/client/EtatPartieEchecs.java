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
    for (int j = 0; j < etatEchiquier[i].length; j++) {
        if (j == 0) {
            etatEchiquier[i][j] = (char) (i + '1'); 
            if(i==8) {
            	 etatEchiquier[i][j] = ' ';
            }
        } else if (i == 0) {
            char[] lowerCaseLetters = {'t', 'c', 'f', 'd', 'r', 'f', 'c', 't'};
            etatEchiquier[i][j] = lowerCaseLetters[j - 1]; 
        } else if (i == 1 || i == 6) {
        	if (i == 1) {
        	    etatEchiquier[i][j] = 'p';
        	} else {
        	    etatEchiquier[i][j] = 'P';
        	} 
        } else if (i > 1 && i < 6) {
            etatEchiquier[i][j] = '.'; 
        } else if (i == 7) {
            char[] upperCaseLetters = {'T', 'C', 'F', 'D', 'R', 'F', 'C', 'T'};
            etatEchiquier[i][j] = upperCaseLetters[j - 1];
        }
        else if (i == 8) {
            char[] upperCaseLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
            etatEchiquier[i][j] = upperCaseLetters[j - 1]; 
        }
    }
}

// Affichage 
for (int i = 0; i < etatEchiquier.length; i++) {
    for (int j = 0; j < etatEchiquier[i].length; j++) {
        matrice.append(etatEchiquier[i][j]).append(' ');
    }
    matrice.append('\n');
}

return (matrice.toString());

    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}