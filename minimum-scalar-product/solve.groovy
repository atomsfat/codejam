//read function
import groovyx.gpars.GParsPool
import java.util.concurrent.*

String name = args[0]

def file = new File(name);

def inputMap = [:]

def countLine = 0
def numCases = 0

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
                currentMap["n"] = line.toInteger()
                intCauseCounter++
            }else if(intCauseCounter == 1){
                currentMap["v1"] = line.split(" ")
                assert currentMap["n"] == currentMap["v1"].size()
                intCauseCounter++
            }else if(intCauseCounter == 2){
                currentMap["v2"] = line.split(" ")
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
//println "--->> $inputMap"

//solver
def result = new ConcurrentHashMap()

//process(inputMap[0], 0 , result)

GParsPool.withPool {
     inputMap.eachParallel { key, value ->
 //    inputMap.each { key, value ->
            process(value, key, result)
       }
}

printOut(result, no_cases,  name)

def process(Map input, int key, Map result){
    def v1 = input.get("v1")*.toLong()
    def v2 = input.get("v2")*.toLong()
    
    v1.sort()
    v2.sort()
    v2 = v2.reverse()

    Long res = 0l
    for(int i=0; i<v1.size();i++){
        
        res +=v1[i]*v2[i]  
    }
    result.putIfAbsent(key, res)

}


def printOut(Map outPut, int no_cases, String name){

        for ( i in 0..(no_cases-1) ) {
            def out1 = outPut[i]    
            println "Case #${i+1}: $out1"
        }

}
