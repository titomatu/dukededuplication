package io.tamatu.duke;

import no.priv.garshol.duke.Processor;
import no.priv.garshol.duke.Property;
import no.priv.garshol.duke.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import no.priv.garshol.duke.utils.TestUtils;
import no.priv.garshol.duke.comparators.ExactComparator;
import no.priv.garshol.duke.PropertyImpl;
import no.priv.garshol.duke.ConfigurationImpl;

public class DukeDedupTest {
    public static TestUtils.TestListener exactListener;
    public static ConfigurationImpl exactMatchConfig;
    public static Processor exactMatchProcessor;

    public static void setup() {
        //setup
        List<Property> exactMatchProperties = new ArrayList<Property>();

        exactListener = new TestUtils.TestListener();

        ExactComparator exactComparator = new ExactComparator();

        //create properties (columns), with name, comparator, and high-low thresholds. ID property has no comparator or propabilities.

        exactMatchProperties.add(new PropertyImpl("ID"));
        exactMatchProperties.add(new PropertyImpl("NAME", exactComparator, 0.0, 1.0));
        exactMatchProperties.add(new PropertyImpl("EMAIL", exactComparator, 0.0, 1.0));

        //create new configuration implementation
        exactMatchConfig = new ConfigurationImpl();

        //add properties to config
        exactMatchConfig.setProperties(exactMatchProperties);
        exactMatchConfig.setThreshold(1.0);
        exactMatchConfig.setMaybeThreshold(0.0);

        //initialize the processor and add match listener
        exactMatchProcessor = new Processor(exactMatchConfig, true);
        exactMatchProcessor.addMatchListener(exactListener);
    }

    public static void testExactMatch() {
        Collection<Record> records = new ArrayList<Record>();
        Record rec1 = TestUtils.makeRecord("ID", "1", "NAME", "Jon", "EMAIL", "jon@doe.com");
        Record rec2 = TestUtils.makeRecord("ID", "1", "NAME", "Jon", "EMAIL", "jon@doe.com");

        records.add(rec1);
        records.add(rec2);

        exactMatchProcessor.deduplicate(records);
        System.out.println(exactListener.getMatches().size());
    }

    public static void main(String[] args){
        setup();
        testExactMatch();
    }
}
