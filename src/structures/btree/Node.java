package structures.btree;

import structures.list.List;

public class Node {
    public String word;
    public List lines;
    Node left, right;

    Node(String value, int line){
        this.word = value;
        lines = new List();
        lines.add(line);
        left = right = null;
    }

    public void put( String value,int line ) {
        //verifica se a palavra passada é de ordem menor que a do nó atual se for nulo cria um novo nó na esquerda
        if ( value.compareToIgnoreCase( this.word ) < 0 ) {
            if ( left != null ) {
                left.put(value,line);
            }
            else {
                left = new Node( value, line );
            }
        }//verifica se a palavra passada é de ordem maior que a do nó atual se for nulo cria um novo nó na direita
        else if ( value.compareToIgnoreCase( this.word ) > 0 ) {
            if ( right != null ) {
                right.put( value, line );
            }
            else
            {
                right = new Node( value, line );
            }
        }
        else {//se nenhum dos casos acima for atendido adiciona o no será igual a palavra então é necessário apenas adicionar uma linha ao indice
            this.lines.add(line);
        }
    }

    public Node get( String key )
    {
        if ( this.word.compareToIgnoreCase( key )==0 )
        {
            return this;
        }

        if ( key.compareTo( this.word ) < 0 )
        {
            return left == null ? null : left.get( key );
        }
        else
        {
            return right == null ? null : right.get( key );
        }
    }
}
