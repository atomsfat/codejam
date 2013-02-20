String name = args[0]
def file = new File(name);

def lines = 0
file.eachLine{
  lines++
}
String[] data= new String[lines]
lines=0;
file.eachLine{line ->
  data[lines] = line
  lines++
}

println data.length

def qf = new QuickFindUF(data.length)

for(String str : data) {
  String[] dt = str.split(" ")
  println dt
  qf.union(Integer.parseInt(dt[0]), Integer.parseInt(dt[1]))
}

println qf.conneted(3,9)

class QuickFindUF{
  int[] id

  public QuickFindUF(int N){
    id = new int[N]
    for(int i=0; i<N; i++)
      id[i] = i
  }

  public boolean conneted(int p, int q){
    return id[p] == id[q]
  }
  public void union(int p, int q){
    int pid = id[p]
    int qid = id[q]
    for(int i=0; i< id.length; i ++){
      if(id[i] == pid)
        id[i] = qid
    }
  }
}

