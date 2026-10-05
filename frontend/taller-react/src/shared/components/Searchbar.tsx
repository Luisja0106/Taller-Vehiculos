interface SearchbarProps {
	buttonColor: string;
	placeholder: string;
}

export function Searchbar({ buttonColor, placeholder }: SearchbarProps) {
	return (
		<form className="flex w-4/5 max-w-3xl rounded-full border-0 bg-white p-2 shadow-sm">
			<input
				type="search"
				placeholder={placeholder}
				aria-label="Buscador"
				className="grow border-transparent bg-transparent px-6 py-2 text-base outline-none"
			/>
			<button
				type="submit"
				className={`cursor-pointer rounded-full border-2 px-6 py-0 font-bold transition-(--transition) ${buttonColor}`}
			>
				Buscar
			</button>
		</form>
	);
}
