/* Uppgift från date-it-2021
 * Camilla S
 * 
 * Två csv-filer kombineras till en ny csv-fil med samtliga skolor med grund-skola, för-skola och fritids-hem.
 * Steg 1: Dictionary skapas för att relatera kommunkud men kommunnamn
 * Steg 2: skolfilen läses av där alla skolor som klarar kriteriet skrivs till en lista
 * Steg 3: Resultatet skrivs till en ny CSV-fil
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace Kits
{
    class Program
    {
        static void Main(string[] args)
        {
            string pathKommuner = @"C:\Users\camilla\documents\Kits\kommuner.csv";
            string pathSkolverksamhet = @"C:\Users\camilla\documents\Kits\skolverksamhet.csv";
            string pathResultat = @"C:\Users\camilla\documents\Kits\resultat.csv";
            string[] splitLine;
            string line;
            Dictionary<string, string> kommunDictionary = new Dictionary<string, string>();
            List<string> resultat = new List<string>();

            using (StreamReader reader = new StreamReader(pathKommuner))
            {

                while ((line = reader.ReadLine()) != null)
                {
                    splitLine = line.Split(';');
                    kommunDictionary.Add(splitLine[0], splitLine[1]);
                }
            }
            using (StreamReader reader = new StreamReader(pathSkolverksamhet))
            {
                while ((line = reader.ReadLine()) != null)
                {
                    splitLine = line.Split(';');
                    if (splitLine[2] == "Ja" && splitLine[3] == "Ja" && splitLine[4] == "Ja")
                    {
                        resultat.Add(splitLine[0] + ";" + kommunDictionary[splitLine[0]] + ";" + splitLine[1] + ";" + splitLine[2] + ";" + splitLine[3] + ";" + splitLine[4]);
                    }
                }
            }
            using (StreamWriter writer = new StreamWriter(pathResultat))
            {
                writer.WriteLine("Kod;Kommun;Skolenhetsnamn;Grund-skola;Förskole-klass;Fritids-hem");
                foreach (string school in resultat)
                    writer.WriteLine(school);
            }
        }
    }
}