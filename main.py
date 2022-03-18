import python.core as core
from datetime import date

table_name = "Anime"
bleach_data = {
    "title": "Bleach",
    "creator": "Tite Kubo",
    "release_date": date.fromisoformat("2001-03-15"),
    "description": "soul reapers",
    "episodes": 374,
    "end_date": None
}
op_data = {
    "title": "One Piece",
    "creator": "Eiichiro Oda",
    "release_date": date.fromisoformat("1999-10-20"),
    "description": "pirates",
    "episodes": 1052,
    "end_date": None
}
criteria = {
    "title": "One Piece"
}
core.add_row(table_name, bleach_data)  # add bleach data
core.add_row(table_name, op_data)      # add one piece data
for row in core.get_table(table_name):
    print(row)                         # print every row in table
print()
print(core.search_rows(table_name, criteria))  # search for rows based on criteria
print()
print(core.get_row(table_name, 1))  # search for row based on id
print()
core.update_row(table_name, 1, {"description": "shinigami"})  # update row 
print(core.get_row(table_name, 1))
core.delete_row(table_name, 2)
for row in core.get_table(table_name):
    print(row)
