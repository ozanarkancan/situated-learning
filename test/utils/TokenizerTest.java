package utils;

import org.junit.Test;

public class TokenizerTest{
	
	@Test
	public void testTokenize(){
		
		Tokenizer tokenizer = new Tokenizer();
		String str = tokenizer.clean("face the octagon carpet, move ahead");
		org.junit.Assert.assertEquals("face the octagon carpet , move ahead", str);
		
		str = tokenizer.clean("move forward; turn right,go ahead");
		org.junit.Assert.assertEquals("move forward ; turn right , go ahead", str);
	}

}
