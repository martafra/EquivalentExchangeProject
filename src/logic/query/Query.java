package logic.query;

public abstract class Query{

    public String quote(String string){
        if(string !=null) {
			return "\"" + string + "\"";
		}
		return string;
    }

}