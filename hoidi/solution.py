from timeit import timeit
import pandas as pd


# Tobias Karlsson
# tobiakar snabela student.chalmers.se
# noll sju sex sju åtta elva åtta trettiotre

def method(saveFile):
    kommuner = pd.read_csv("kommuner.csv",
                           sep=';',  # separator is ;
                           converters={'Kod': lambda x: str(x)})  # keep the leading 0 in Kod by making it a string
    skolor = pd.read_csv("skolverksamhet.csv",
                         sep=';',
                         converters={'Kod': lambda x: str(x)})

    # filter out the ones that don't have the specified schools
    result = skolor[
        (skolor['Grund-skola'] == 'Ja') & (skolor['Förskole-klass'] == 'Ja') & (skolor['Fritids-hem'] == 'Ja')].copy()

    # merge with the kommuner dataframe
    result = pd.merge(result, kommuner, on='Kod')

    # setting Kommun as second column
    result = result[['Kod', 'Kommun', 'Skolenhetsnamn', 'Grund-skola', 'Förskole-klass', 'Fritids-hem']]

    # save to result.csv
    if saveFile:
        result.to_csv('result.csv', index=False)


def save_file():
    method(saveFile=True)


def dont_save_file():
    method(saveFile=False)

print("Benchmark:")
runs = 1000
print(f"Average time after {runs} runs while not saving: \n\t{timeit(dont_save_file, number=runs) / runs} seconds")
print(f"Average time after {runs} runs while saving: \n\t{timeit(save_file, number=runs) / runs} seconds")
