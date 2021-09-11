package io.tamatu.duke;

import no.priv.garshol.duke.*;
import no.priv.garshol.duke.matchers.PrintMatchListener;
import no.priv.garshol.duke.utils.TestUtils;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Iterator;

public class DukeDedupTest2 {
    public static void main(String[] args) throws IOException, SAXException {
        Configuration config = //
                ConfigLoader //
                        .load("classpath:namebase.xml"); //

        Processor proc = new Processor(config); //
        TestUtils.TestListener testListener = new TestUtils.TestListener(); //

        proc.addMatchListener(testListener); //
        proc.setThreads(4); //
        proc.deduplicate(); //

        System.out.println("Deduplication starting..."); //
        System.out.println(testListener.getMatches().size()); //
        System.out.println("Deduplication finished..."); //
        //Iterator<TestUtils.Pair> it = testListener.getMatches().iterator();
        //while(it.hasNext()){
        //    TestUtils.Pair pair = it.next();
        //    System.out.println(pair.toString());
        //}
        proc.close();
    }
}
