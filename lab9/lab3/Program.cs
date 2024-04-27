// See https://aka.ms/new-console-template for more information
using System;
using System.Collections.Generic;
using System.Linq;
using System.Xml.Serialization;
using System.Xml;
using System.Xml.Linq;
using System.Xml.XPath;
using System.IO;



public class Program
{
    [XmlArray("cars")]
    static public List<Car> cars = new List<Car>(){
          new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
            new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
            new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
            new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
            new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
            new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
            new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
            new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
            new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
    };

    public static void Main(string[] args)
    {
        var query1 = cars 
            .Where(car => car.Model == "A6")
            .Select(car => new {
                EngineType = car.motor.Model == "TDI" ? "diesel" : "petrol",
                Hppl = car.motor.HorsePower / car.motor.Displacement
            });
        foreach (var car in query1)
        {
            Console.WriteLine(car);
        }
        Console.WriteLine("-------------------------------------------------");
        var query2 = query1.
            GroupBy(car => car.EngineType)
            .Select(group => new {
                EngineType = group.Key,
                AvgHppl = group.Average(car => car.Hppl)
            });
        foreach (var car in query2)
        {
            Console.WriteLine(car);
        }
         Console.WriteLine("-------------------------------------------------");
        XmlRootAttribute root = new XmlRootAttribute("cars");  
        XmlSerializer serializer = new XmlSerializer(typeof(List<Car>), root);
        TextWriter writer = new StreamWriter("CarsCollection.xml");
        serializer.Serialize(writer, cars);
        writer.Close();
        FileStream fileStream = new FileStream("CarsCollection.xml", FileMode.Open);
        List<Car> result = (List<Car>) serializer.Deserialize(fileStream);
         foreach (var item in result)
            {
                Console.WriteLine("({0}, ({1}, {2}, {3}), {4})", item.Model, item.motor.Displacement, item.motor.Model, 
                    item.motor.HorsePower, item.Year);
            }
            fileStream.Close();
        Console.WriteLine("-------------------------------------------------");
        XElement rootNode = XElement.Load("CarsCollection.xml");
            //double avgHP = (double) rootNode.XPathEvaluate("sum(//engine[@model!='TDI']/horsePower) div count(//engine[@model!='TDI']/horsePower)");
            double avgHP = (double) rootNode.XPathEvaluate( "sum(//Engine[@model!='TDI']/horsePower) div count(//Engine[@model!='TDI']/horsePower)");
            Console.WriteLine(avgHP);
             Console.WriteLine("-------------------------------------------------");

            IEnumerable<XElement> models = rootNode.XPathSelectElements("//car/model[not(. = ../preceding-sibling::car/model)]"); 
            foreach (XElement model in models)
            {
                Console.WriteLine(model.Value);
            }
            Console.WriteLine("-------------------------------------------------");
            createXmlFromLinq(cars);
            Console.WriteLine("Created xml from LINQ");
            Console.WriteLine("-------------------------------------------------");
            createTableFromLinq(cars);
            Console.WriteLine("Created table from LINQ");
            Console.WriteLine("-------------------------------------------------");
    XDocument doc = XDocument.Load("CarsCollection.xml");
    foreach (XElement hp in doc.Descendants("horsePower"))
    {
hp.Name = "hp";
    }          
    foreach (XElement year in doc.Descendants("year"))
    {
        foreach (XElement model in year.Parent.Descendants("model"))
        {
            model.SetAttributeValue("year", year.Value);
        }
    }
    doc.Descendants("year").Remove();
    doc.Save("CarsCollectionModified.xml");   
    Console.WriteLine("Created Modified xml file");
    Console.WriteLine("-------------------------------------------------");
            // "sum(//engine[@model!='TDI']/horsePower) div count(//engine[@model!='TDI']/horsePower)");
    }
    private static void createXmlFromLinq(List<Car> myCars) { 
        IEnumerable<XElement> nodes =  from car in myCars 
        select new XElement("car", 
                    new XElement("model", car.Model),
                    new XElement("Engine",
                        new XAttribute("model", car.motor.Model),
                        new XElement("horsePower", car.motor.HorsePower),
                        new XElement("displacement", car.motor.Displacement)),
                    new XElement("year", car.Year)); // zapytanie LINQ
        XElement rootNode = new XElement("cars", nodes); // stwórz węzeł
        rootNode.Save("CarsFromLinq.xml");
         }
        public static void createTableFromLinq(List<Car> myCars)
        {
            IEnumerable<XElement> nodes = from car in myCars
               select new XElement("tbody",
               
                new XElement("td", car.Model),
                new XElement("td", car.Year),
            new XElement("tr",
                new XElement("td", car.motor.Model),
                new XElement("td", car.motor.HorsePower),
                new XElement("td", car.motor.Displacement)));
            XElement rootNode = new XElement("table", nodes); //create a root node to contain the query results
            rootNode.Save("CarsFromLinq.html");
        }

}