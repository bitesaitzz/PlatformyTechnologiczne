using System;
using System.Xml.Serialization;

[XmlType("car")]
public class Car{

    public Car(){}
    public Car(string Model, Engine engine, int year){
        this.Model = Model;
        this.motor = engine;
        this.Year = year;
    }
    [XmlElement("model")]
    public string Model { get; set; }
    [XmlElement("Engine")]
    public Engine motor { get; set; }
    [XmlElement("year")]
    public int Year { get; set; }


}