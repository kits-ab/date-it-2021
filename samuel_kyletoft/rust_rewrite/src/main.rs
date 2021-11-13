use std::{collections::HashMap, env, error, fmt, fs, io, io::Read, io::Write};

fn main() {
	if let Err(e) = do_work() {
		eprintln!("{}", e);
	}
}

fn do_work() -> Result<(), Box<dyn error::Error>> {
	let args = env::args().skip(1).collect::<Vec<_>>();
	if args.len() != 2 {
		eprintln!(
			"Usage: cargo run <municipalities> <schools>\n\
			Prints to stdout so to store to a file redirect like this:\n\
			cargo run <municipalities> <schools> > <output>",
		);
		return Err(Box::new(Error::ArgumentFormat));
	}

	let mut municipalities_buffer = String::new();
	let mut municipalities_file = fs::File::open(&args[0])?;
	municipalities_file.read_to_string(&mut municipalities_buffer)?;
	let municipalities = municipalities_buffer
		.lines()
		.map(|line| {
			let mut split = line.split(";");
			Some((split.next()?, split.next()?))
		})
		.collect::<Option<HashMap<_, _>>>()
		.ok_or(Error::MunicipalityParse)?;

	let mut schools_buffer = String::new();
	let mut schools_file = fs::File::open(&args[1])?;
	schools_file.read_to_string(&mut schools_buffer)?;

	let stdout = io::stdout();
	let mut stdout = stdout.lock();

	println!("{}", schools_buffer.lines().next().unwrap());
	for line in schools_buffer.lines().skip(1) {
		let code = &line[..4];
		let municipality = municipalities
			.get(code)
			.ok_or(Error::MunicipalityExists(code.to_string()))?;
		writeln!(stdout, "{};{}{}", code, municipality, &line[4..])?;
	}

	Ok(())
}

#[derive(Debug, Clone, PartialEq)]
enum Error {
	ArgumentFormat,
	MunicipalityParse,
	MunicipalityExists(String),
}

impl error::Error for Error {}

impl fmt::Display for Error {
	fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
		use Error::*;
		match self {
			ArgumentFormat => write!(f, "Malformed arguments"),
			MunicipalityExists(s) => write!(f, "Municipality cannot be found for code: {}", s),
			MunicipalityParse => write!(f, "Municipality parse error"),
		}
	}
}
