Solution for date-it-2021 written in Python (.ipynb notebook) with use of pandas
7 lines of code, not counting comments, imports and new lines (to make it look pretty c:)

Name:    Nils Trubkin
Github:  https://github.com/nils-trubkin
Website: https://nils.tk
E-mail:  nils.trubkin@gmail.com
Phone:   070-279-53-86

Here is a brief overview of the code inside the notebook.ipynb

import pandas as pd
# Load the .csv files
dk = pd.read_csv('kommuner.csv', sep=';')
ds = pd.read_csv('skolverksamhet.csv', sep=';')

# Merge the DataFrames
m = pd.merge(dk, ds, on="Kod")
# Filter and copy
res = m[(m["Grund-skola"] == "Ja") &
        (m["Förskole-klass"] == "Ja") &
        (m["Fritids-hem"] == "Ja")].copy()

# Pad with zeros to a 4-digit number on 'Kod'
res['Kod'] = res['Kod'].astype(str).str.pad(4,fillchar='0').copy()
# Set index to avoid automatically generated ids
res = res.set_index('Kod')
# Save result to file
res.to_csv('result.csv', sep=';')