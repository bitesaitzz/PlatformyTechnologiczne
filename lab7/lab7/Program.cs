
using System;
using System.Runtime.Serialization.Formatters.Binary;
#pragma warning disable SYSLIB0011

namespace lab7{
   
    class Program{
        
      
        public static void Main(string[] args){
            string path = ".";
            if (args.Length != 0){
                path = args[0];
            }
            
            DirectoryInfo dir = new DirectoryInfo(path);
            Console.WriteLine(dir.FullName);
            displayFolder(path, 1 );
            Console.WriteLine("Oldest file: " + dir.getOldestFile(DateTime.Now));
            Console.WriteLine("Directory elements:");
            SortedDictionary<string, long> curDir = new SortedDictionary<string, long>();
            curDir = LoadDirectoryElements(dir);

            BinaryFormatter formatter = new BinaryFormatter();
using (Stream stream = new FileStream("MyFile.bin", FileMode.Create, FileAccess.Write, FileShare.None))
{
    formatter.Serialize(stream, curDir);
}
SortedDictionary<string, long> deserializedCollection;
using (Stream stream = new FileStream("MyFile.bin", FileMode.Open, FileAccess.Read, FileShare.Read))
{
    deserializedCollection = (SortedDictionary<string, long>)formatter.Deserialize(stream);
}

foreach (var pair in deserializedCollection)
{
    Console.WriteLine($"{pair.Key} -> {pair.Value}");
}



            /*
            using (BinaryWriter writer = new BinaryWriter(File.Open("MyFile.bin", FileMode.Create)))
{
    writer.Write(curDir.Count);
    foreach (var pair in curDir)
    {
        writer.Write(pair.Key);
        writer.Write(pair.Value);
    }
}

// Десериализация коллекции
SortedDictionary<string, long> deserializedCollection = new SortedDictionary<string, long>(new dirLenght());
using (BinaryReader reader = new BinaryReader(File.Open("MyFile.bin", FileMode.Open)))
{
    int count = reader.ReadInt32();
    for (int i = 0; i < count; i++)
    {
        string key = reader.ReadString();
        long value = reader.ReadInt64();
        deserializedCollection.Add(key, value);
    }
}

// Вывод содержимого коллекции после десериализации
foreach (var pair in deserializedCollection)
{
    Console.WriteLine($"{pair.Key} -> {pair.Value}");
}
*/



            
        }

        public static void printTabs(int depth){
            for (int i = 0; i < depth; i++){
                Console.Write("\t");
            }
        }

        public static void displayFolder(string path, int depth){
            printTabs(depth);
            DirectoryInfo dir = new DirectoryInfo(path);
             Console.WriteLine(dir.Name +getSizeDir(dir) + " "+ dir.getDOS());
            var dirs = dir.GetDirectories();
            for(int i = 0; i < dirs.Length; i++){
            
                displayFolder(dirs[i].FullName, depth + 1);
            }
            foreach (var file in dir.GetFiles()){
                printTabs(depth+1);
                Console.WriteLine(file.Name +getSizeFile(file) + " "+ file.getDOS());
                
               
            }
            }

        public static string getSizeDir(DirectoryInfo dir){
            string[] files = dir.GetFiles().Select(file => file.FullName).ToArray();
            string[] dirs = dir.GetDirectories().Select(dir => dir.FullName).ToArray();
            return "(" + (dirs.Length + files.Length).ToString()+")";
        
        }
        public static string getSizeFile(FileInfo file){
            double size = file.Length/1024.0;
            string sizeStr = size.ToString("0.000");
            return "("+sizeStr+" KB)";
        }


        public static SortedDictionary<string, long> LoadDirectoryElements(DirectoryInfo dir){
            var comparer = new dirLenght();
            var elements = new SortedDictionary<string, long>(comparer);
            foreach (var file in dir.GetFiles()){
                elements.Add(file.Name, file.Length);
            }
            foreach (var d in dir.GetDirectories()){
                elements.Add(d.Name, d.GetFiles().Length + d.GetDirectories().Length);
        }
        return elements;

    }}

[Serializable]
        public class dirLenght : IComparer<string>{
            public int Compare(string x, string y){
                int lengthDifference = x.Length - y.Length;
        if (lengthDifference == 0)
        {
            return string.Compare(x, y, StringComparison.Ordinal);
        }
        else
        {
            return lengthDifference;
        }
            }
        }

         public static class  DirectoryInfoExtensions{
            public static DateTime getOldestFile(this DirectoryInfo dir, DateTime oldest){
                
             
            var dirs = dir.GetDirectories();
            for(int i = 0; i < dirs.Length; i++){
               
                if(dirs[i].CreationTime < oldest){
                    oldest = dirs[i].CreationTime;
                }
                oldest = getOldestFile(dirs[i], oldest);
            }
            foreach (var file in dir.GetFiles()){
                if (file.CreationTime < oldest){
                    oldest = file.CreationTime;
                }
               
            }
              
              return oldest;
            }
         }

         public static class FileSystemInfoExtensions{
            public static string getDOS(this FileSystemInfo file){
                FileAttributes attributes = File.GetAttributes(file.FullName);
                string r = "-", a="-", h="-", s="-";
                if ((attributes & FileAttributes.ReadOnly) == FileAttributes.ReadOnly){
                    r = "r";
                }
                if ((attributes & FileAttributes.Archive) == FileAttributes.Archive){
                    a = "a";
                }
                if ((attributes & FileAttributes.Hidden) == FileAttributes.Hidden){
                    h = "h";
                }
                if ((attributes & FileAttributes.System) == FileAttributes.System){
                    s = "s";
                }
                return r + a + h + s;
            }                
         }


    }
#pragma warning restore SYSLIB0011