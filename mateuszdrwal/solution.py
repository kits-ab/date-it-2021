import csv

kommuner = {}

with open("kommuner.csv") as f:
    reader = csv.reader(f, delimiter=";")
    next(reader)  # skip first row with legend
    for row in reader:
        kommuner[row[0]] = row[1]

with open("skolverksamhet.csv") as skolverksamhet:
    reader = csv.reader(skolverksamhet, delimiter=";")
    next(reader)  # skip first row with legend

    with open("skolor.csv", "w") as skolor:
        writer = csv.writer(skolor, delimiter=";")
        writer.writerow(
            [
                "Kod",
                "Kommun",
                "Skolenhetsnamn",
                "Grund-skola",
                "Förskole-klass",
                "Fritids-hem",
            ]
        )

        for row in reader:
            if row[2] == row[3] == row[4] == "Ja":
                writer.writerow(
                    [
                        row[0],
                        kommuner[row[0]],
                        row[1],
                        row[2],
                        row[3],
                        row[4],
                    ]
                )
