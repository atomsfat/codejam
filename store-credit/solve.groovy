//read function
import groovyx.gpars.GParsPool
import java.util.concurrent.*
def file = new File("A-large-practice.in");
def inputMap = [:]

def countLine = 0
def numCases = 0
def numLineByCase = 3
def processCase = false

def no_cases = 0
def intCauseCounter = 0

file.eachLine{ line->
    
    if(countLine == 0 ){
        numCases = line    
    }else{
        def currentMap = inputMap[no_cases]
        if(!currentMap){
            currentMap = [:]
        }
            if(intCauseCounter==0){
                currentMap["credit"] = line
                intCauseCounter++
            }else if(intCauseCounter == 1){
                currentMap["no_items"] = line
                intCauseCounter++
            }else if(intCauseCounter == 2){
                currentMap["price_items"] = line
                intCauseCounter = 0;
            }
      inputMap[no_cases] = currentMap
        if((countLine-0)%3==0){
            no_cases++
        }
    }
    countLine++
}
assert numCases.toInteger() == inputMap.size()
println "--->> $inputMap"

//solver
def result = new ConcurrentHashMap()

GParsPool.withPool {
     inputMap.eachParallel { key, value ->
            process(value, key, result)
       }
}

printOut(result, no_cases)

def process(Map input, int key, Map result){
    def price_items = input.get("price_items").split(" ")*.toInteger()
    def credit = input.get("credit").toInteger()
    def price_items_sorted = new ArrayList(price_items).sort()

    println "---> $price_items credit $credit sorted $price_items_sorted"
    price_items_sorted.find{ value->
    
      if(value<credit){
        int busca = credit - value;
            if(busca == value){
                int index =  price_items.findIndexOf{it == busca}
                int index1 =  price_items.findLastIndexOf{it == busca}
                    def list = [++index, ++index1]
                     result.putIfAbsent(key, list)
                     println "resultado $list"
                if(index1 != index){
                    return true //break    
                }
            }else{
               int index =  price_items.findIndexOf{it == busca}
                if(index>0){
                  int index1= price_items.findIndexOf{it == value}
                    def list = [++index, ++index1]
                     result.putIfAbsent(key, list)
                     println "resultado $list"
                    return true //break
                }
            }
    }
    return false //keep looping
    }
}


def printOut(Map outPut, int no_cases){

    f = new File('out.txt')
    
    f.withWriter { out ->

        for ( i in 0..(no_cases-1) ) {
        
            def out1 = outPut[i].sort()    
        
            out.println "Case #${i+1}: ${out1[0]} ${out1[1]}"
        }

    }

    println outPut   
}
