package listofbusiness

class ReadCSVService {

    def readCSV(InputStreamReader isr){
        BufferedReader br = new BufferedReader(isr)
        String[] strings = br.readLines()
        List<Business> bList = new ArrayList<Business>() {}
        strings.each {
            def values = it.split(';')
            Business b = new Business(
                    name: values[0],
                    email: values[1],
                    street: values[2])
            if (values.size()==4){
                b.zip =values[3]
            }
            bList<<b
        }
        bList.remove(0)
        return bList
    }
}
