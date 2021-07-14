import java.io.*; 
import java.util.*; 
import java.lang.Math;


// Tree node
class Node {
  int id;
  int level;
  Node bossnode;
  Node left;
  Node right;
  int height;
  Vector<Node> employees;
  Node(int key){
	  id=key;
	  left= null;
	  right=null;
	  bossnode=null;
	  height=1;
	  employees = new Vector<Node> ();
	  
  }
    
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
Node root=null;
Node Owner;
int Size=0;



public Node search(int id, Node root){
	if (root == null)
		return null;
	if (root.id == id)
		return root;
	
	else if (root.id < id)
		return search(id, root.right);
	else 
		return search(id, root.left);
		
}
int height(Node N) {
        if (N == null)
            return 0;
 
        return N.height;
    }
int getBalance(Node N) {
        if (N == null)
            return 0;
 
        return height(N.left) - height(N.right);
    }


private Node leftRotate(Node node){
	
	Node y = node.right;
    Node subtree = y.left;
   
    y.left = node;
    node.right = subtree;
	
    //int nodelheight, noderheight, ylheight, yrheight;
	//if(node.left==null )
	//	nodelheight=0;
	//else
	//	nodelheight=node.left.height;
	//if(node.right==null )
	//	noderheight=0;
	//else
	//	noderheight=node.right.height;
	
	//if(y.left==null )
	//	ylheight=0;
	//else
	//	ylheight=y.left.height;
	//if(y.right==null )
	//	yrheight=0;
	//else
	//	yrheight=y.right.height;
	
    //node.height = Math.max(nodelheight, noderheight) + 1;
    //y.height = Math.max(ylheight, yrheight) + 1;
	node.height = Math.max(height(node.left), height(node.right)) + 1;
    y.height = Math.max(height(y.left), height(y.right)) + 1;
    return y;
    
}

private Node rightRotate(Node y){
	Node node = y.left;
    Node subtree = node.right;
        
    node.right = y;
    y.left = subtree;
	
	int nodelheight, noderheight, ylheight, yrheight;
	if(node.left==null )
		nodelheight=0;
	else
		nodelheight=node.left.height;
	if(node.right==null )
		noderheight=0;
	else
		noderheight=node.right.height;
	
	if(y.left==null )
		ylheight=0;
	else
		ylheight=y.left.height;
	if(y.right==null )
		yrheight=0;
	else
		yrheight=y.right.height;
 
    y.height = Math.max(ylheight, yrheight) + 1;
    node.height = Math.max(nodelheight, noderheight) + 1;

    return node;
}


private Node insert(Node root,int id, Node boss) {
	
	if(root==null){
		
		Node temp = new Node(id);
		temp.level= boss.level+1;
		temp.bossnode = boss;
		boss.employees.add(temp);
		
		return temp;
		
	}
	
	if(root.id < id)
		root.right = insert(root.right, id, boss);
	else if(root.id > id)
		root.left = insert(root.left, id , boss);
	else
		return root;
	
	//int lheight, rheight;
	
	//if(root.left==null)
	//	lheight = 0;
	//else
	//	lheight = root.left.height;
	//if( root.right == null)
	//	rheight =0;
	//else
	//	rheight = root.right.height;
	//root.height = Math.max( lheight, rheight )+1;
	//int heightdiff;
	
	//if(root == null)
	//	heightdiff=0;
	//else
	//	heightdiff= lheight-rheight;
	
	//if (heightdiff > 1 && id < root.left.id)
	//	return rightRotate(root);
 
    root.height = 1 + Math.max(height(root.left),height(root.right));
	int heightdiff = getBalance(root);
	if (heightdiff < -1 && id > root.right.id)
        return leftRotate(root);
 
   
    if (heightdiff > 1 && id > root.left.id) {
		
        root.left = leftRotate(root.left);
        return rightRotate(root);
		
    }
 

    if (heightdiff < -1 && id < root.right.id) {
        root.right = rightRotate(root.right);
        return leftRotate(root);
    }
 
        
    return root;
}


private Node delete(Node root, int id){
	
	if (root == null) 
        return root; 
  
	
	if (id < root.id) 
        root.left = delete(root.left, id); 
  
    else if (id > root.id) 
        root.right = delete(root.right, id); 
        
    else
		{ 
    
        if ((root.left == null) || (root.right == null)) 
        { 
			Node temp;
            if (root.left == null) 
                temp = root.right; 
            else
                temp = root.left; 
  
            if (temp == null) 
            { 
                temp = root; 
                root = null; 
            } 
            else 
                root = temp; 
        } 
        else
        { 
      
            Node temp = root.right; 
  
			while (temp.left != null) 
				temp = temp.left; 
  
            
            root.id = temp.id; 
			root.bossnode=temp.bossnode;
			root.level=temp.level;
			root.employees=temp.employees;
  
            root.right = delete(root.right, temp.id); 
        } 
        } 
  
        
        if (root == null) 
            return root; 
  
       
		//int lheight, rheight,lrheight,llheight,rrheight,rlheight;
		//if(root.left==null){
			//lheight = 0;
			//llheight=-1;
			//lrheight=-1;
		//}
		//else{
		//	lheight = root.left.height;
		//	if(root.left.left==null)
		//		llheight=0;
		//	else
		//		llheight=root.left.left.height;
		//	if(root.left.right==null)
		//		lrheight=0;
		//	else
		//		lrheight=root.left.right.height;
		//}
		//if( root.right == null){
		//	rheight =0;
		//	rlheight=-1;
		//	rrheight=-1;
		//}
		//else{
		//	rheight = root.right.height;
		//	if(root.right.left==null)
		//		rlheight=0;
		//	else
		//		rlheight=root.right.left.height;
		//	if(root.right.right==null)
		//		rrheight=0;
		//	else
		//		rrheight=root.right.right.height;
		//}	
		if (root == null) 
            return root; 
  
        
        root.height = Math.max(height(root.left), height(root.right)) + 1; 
		//root.height = Math.max( lheight, rheight )+1;
	
		
  
        
        //int heightdiff = lheight-rheight; 
		int heightdiff = getBalance(root); 
        
		
		//int lheightdiff = llheight- lrheight;
		//int rheightdiff= rlheight- rrheight;
		
        if (heightdiff > 1 && getBalance(root.left) >= 0) 
            return rightRotate(root); 
  
       
        if (heightdiff > 1 && getBalance(root.left) < 0) 
        { 
            root.left = leftRotate(root.left); 
            return rightRotate(root); 
        } 
  
       
        if (heightdiff < -1 && getBalance(root.right) <= 0) 
            return leftRotate(root); 
  
    
        if (heightdiff < -1 && getBalance(root.right) > 0) 
        { 
            root.right = rightRotate(root.right); 
            return leftRotate(root); 
        } 
  
        return root; 
} 
	
	
	


public boolean isEmpty(){
	//your implementation
	return size()==0;
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
} 

public int size(){
	//your implementation
	return Size;
	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public int level(int id) throws IllegalIDException, EmptyTreeException{
	//your implementation
	if (isEmpty())
		throw new EmptyTreeException("the heirarchy is empty");
	
	Node NodeOfId= search(id,root);
	if (NodeOfId == null)
		throw new IllegalIDException("asking for level of invalid Id");
	else
		return NodeOfId.level;
	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public void hireOwner(int id) throws NotEmptyException{
	//your implementation
	if (!isEmpty())
		throw new NotEmptyException("Owner of the organisation already exists");
	else{
		Owner = new Node(id);
		Owner.level=1;
		root= Owner;
		Size=1;
	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}}

public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
	//your implementation
	if(root==null)
		throw new EmptyTreeException("trying to perform hireEmployee when tree is empty");
	
	Node NodeOfBoss=search(bossid, root);
	Node NodeOfId=search(id, root);
	if(NodeOfBoss==null)
		throw new IllegalIDException("trying to hire emplyee under invalid bossid");
	if(NodeOfId!= null)
		throw new IllegalIDException("trying to hire already existing id");
	else{
	
		root=insert(root, id, NodeOfBoss);		
		Size++;
		
		
	
	}
	
	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{
	//your implementation
	
	if(root==null)
		throw new EmptyTreeException("firing from an empty tree");
	
	else if(id==Owner.id)
		throw new IllegalIDException("Owner cannot be fired");
	
	Node temp=search(id,root);
	
	if(temp == null)
		throw new IllegalIDException("firing an id that doesnt exit");
	else{
		
		
		
		
		//System.out.println(temp.bossnode.employees.remove(temp));
		//System.out.println(temp.bossnode.employees);
		for(int i=0; i<temp.bossnode.employees.size(); i++){
			if(temp.id==temp.bossnode.employees.get(i).id){
				temp.bossnode.employees.remove(i);
				break;
			}
			//System.out.println(temp.bossnode.employees.get(i).id);
		}
		root= delete(root, id);
		//System.out.println(search(id, root));
		Size--;
		
	}
	
	
 	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}


public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
	//your implementation
	if(root==null)
		throw new EmptyTreeException("firing from an empty tree");
	if(id==Owner.id || manageid==id)
		throw new IllegalIDException("Owner cannot be fired");
	
	Node temp=search(id,root);
	Node temp2= search(manageid, root);
	
	if(temp == null || temp2==null )
		throw new IllegalIDException("firing an id that doesnt exit or manage id doesnt exist");
	else{
		
		
		for(int i=0; i< temp.employees.size(); i++){
			temp.employees.get(i).bossnode=temp2;
			temp2.employees.add(temp.employees.get(i));
		}
		for(int i=0; i<temp.bossnode.employees.size(); i++){
			if(temp.id==temp.bossnode.employees.get(i).id){
				temp.bossnode.employees.remove(i);
				break;
			}
			//System.out.println(temp.bossnode.employees.get(i).id);
		}
		
		}
		
		root = delete(root, id);
		
		Size--;
		
	}
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
 

public int boss(int id) throws IllegalIDException,EmptyTreeException{
	//your implementation
	//correction1
	if( isEmpty())
		throw new EmptyTreeException("the tree is empty");
	else if (id== Owner.id)
		return -1;
	
	
	Node NodeOfId= search(id, root);
	
	if ( NodeOfId == null)
		throw new IllegalIDException("the id whose boss we need doesn't exist");
	else
		
		return NodeOfId.bossnode.id;
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}



public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
	//your implementation
	if(root==null)
		throw new EmptyTreeException("lowestCommonBoss is called on an empty tree");
	
	if( Owner.id == id1 || Owner.id == id2)
		return -1;
	
	Node Nodeid1 = search(id1, root);
	Node Nodeid2 = search(id2, root);
	
	
	
	if(Nodeid1==null || Nodeid2==null)
		throw new IllegalIDException("id for which lowestCommonBoss is called doesnt exist");
	
	if (Nodeid1.level==Nodeid2.level){
		if (Nodeid1.id==Nodeid2.id)
			return Nodeid2.bossnode.id;
		
		while(Nodeid1.id != Nodeid2.id){
			Nodeid1=Nodeid1.bossnode;
			Nodeid2=Nodeid2.bossnode;
		}
		return Nodeid1.id;
		
	}else if(Nodeid1.level<Nodeid2.level){
		
		while(Nodeid1.level<Nodeid2.level)
			Nodeid2=Nodeid2.bossnode;
		//correction2
		Nodeid2 =Nodeid2.bossnode;
		Nodeid1= Nodeid1.bossnode;
		while(Nodeid1.id != Nodeid2.id){
		Nodeid1=Nodeid1.bossnode;
		Nodeid2=Nodeid2.bossnode;
		}
		
		return Nodeid1.id;
		
	}else{
		while(Nodeid1.level>Nodeid2.level)
			Nodeid1=Nodeid1.bossnode;
		
		Nodeid2 =Nodeid2.bossnode;
		Nodeid1= Nodeid1.bossnode;
		while(Nodeid1.id != Nodeid2.id){
		Nodeid1=Nodeid1.bossnode;
		Nodeid2=Nodeid2.bossnode;
		}
		
		return Nodeid1.id;
		
	}
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public String toString(int id) throws IllegalIDException, EmptyTreeException{
	//your implementation
	if( root ==null)
		throw new EmptyTreeException("toString on empty tree");
	
	Node NodeOfId = search(id , root);
	
	if( NodeOfId == null)
		throw new IllegalIDException(" toString on illegal id");
	
	String s= String.valueOf(NodeOfId.id) + Character.toString(',');
	
	Vector<Node> que= new Vector<Node>();
	que.add(NodeOfId);
	Vector<Integer> levelnum= new Vector<Integer>();
	
	Vector<Integer> v= new Vector<Integer>();
	v.add(NodeOfId.id);
	while( !que.isEmpty()){
		Node front=que.remove(0);
		if(levelnum.size()-1<front.level-NodeOfId.level)
			levelnum.add(1);
		else
			levelnum.set( front.level-NodeOfId.level,levelnum.get(front.level-NodeOfId.level)+1);
		for(int i=0; i<front.employees.size(); i++){
			que.add(front.employees.get(i));
			v.add(front.employees.get(i).id);
		}	
	}
	int count=1;
	for(int i=0; i< levelnum.size()-1; i++){
		
		
		Vector<Integer> temp= new Vector<Integer> (v.subList(count,levelnum.get(i+1)+count));
		count+=levelnum.get(i+1);
		Collections.sort(temp);
		for(int j=0; j<temp.size()-1; j++){
			s+=String.valueOf(temp.get(j))+Character.toString(' ');
		}
		
		s+=String.valueOf(temp.get(temp.size()-1))+Character.toString(',');
	}
	

	return s.substring(0,s.length()-1);
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

}
